package com.utc.cards.player.jade;

import static com.utc.cards.Constants.CARDS_PLAYER_AGENT_NAME;
import static com.utc.cards.Constants.CARDS_PLAYER_HELPER_AGENT_NAME;
import static com.utc.cards.Constants.HOST_IP;
import static com.utc.cards.Constants.HOST_PORT;
import static com.utc.cards.Constants.JADE_CARDS_PREFS_FILE;
import static com.utc.cards.Constants.LOCAL_IP;
import jade.android.AndroidHelper;
import jade.android.MicroRuntimeService;
import jade.android.MicroRuntimeServiceBinder;
import jade.android.RuntimeCallback;
import jade.core.Agent;
import jade.core.MicroRuntime;
import jade.core.Profile;
import jade.util.leap.Properties;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;

import com.utc.cards.R;
import com.utc.cards.model.PlayerModel;
import com.utc.cards.player.jade.agent.playerAgent.PlayerAgent;
import com.utc.cards.player.jade.agent.playerHelperAgent.PlayerHelperAgent;
import com.utc.cards.utils.Utils;

public class PlayerAgentManager
{

    private static Logger _log = LoggerFactory
	    .getLogger(PlayerAgentManager.class);

    private static PlayerAgentManager _instance;

    private MicroRuntimeServiceBinder _microRuntimeServiceBinder;
    private ServiceConnection _serviceConnection;

    private int readyAgents = 0;
    private Map<String, Class<? extends Agent>> agentClassMap = new HashMap<String, Class<? extends Agent>>();

    public static PlayerAgentManager instance()
    {
	if (_instance == null)
	{
	    _instance = new PlayerAgentManager();
	}
	return (PlayerAgentManager) _instance;
    }

    private PlayerAgentManager()
    {
	super();
	agentClassMap.put(CARDS_PLAYER_AGENT_NAME, PlayerAgent.class);
	agentClassMap.put(CARDS_PLAYER_HELPER_AGENT_NAME,
		PlayerHelperAgent.class);
    }

    // seule méthode visible, utilisée pour démarrer tous les agents du player
    public void startAgents(final Activity activity, String gmail,
	    final AgentActivityListener agentActivityListener)
    {
	readyAgents = 0;
	final RuntimeCallback<AgentController> agentStartupCallback = new RuntimeCallback<AgentController>() {

	    @Override
	    public void onSuccess(AgentController agent)
	    {
		_log.debug("startAgents() : {}/{}", readyAgents,
			agentClassMap.size());
		if (readyAgents == agentClassMap.size())
		{
		    _log.debug("agentStartupCallback.onSuccess() : all agents ready !");
		    agentActivityListener.onAllAgentsReady();
		}
	    }

	    @Override
	    public void onFailure(Throwable throwable)
	    {
		_log.warn("agentStartupCallback.onFailure() : Failed to start at least one agent");
	    }
	};
	_log.debug("startAgents() ...");

	startJadePlatform(activity, agentStartupCallback, gmail,
		new PlayerModel());
	_log.debug("startAgents() DONE");
    }

    protected Properties buildJadeProfile(Activity activity)
    {
	_log.debug("buildJadeProfile() ...");
	SharedPreferences settings = activity.getSharedPreferences(
		JADE_CARDS_PREFS_FILE, Context.MODE_PRIVATE);
	String localIpAddress = settings.getString(LOCAL_IP, "");
	String mainContainerHost = settings.getString(HOST_IP, "");
	String mainContainerPort = settings.getString(HOST_PORT, "");
	// PLAYER version
	final Properties profile = new Properties();
	_log.info("JADE properties :");
	_log.info(Profile.MAIN_HOST + " = " + mainContainerHost);
	_log.info(Profile.MAIN_PORT + " = " + mainContainerPort);
	_log.info(Profile.MAIN + " = " + Boolean.FALSE.toString());
	_log.info(Profile.JVM + " = " + Profile.ANDROID);
	profile.setProperty(Profile.MAIN_HOST, mainContainerHost);
	profile.setProperty(Profile.MAIN_PORT, mainContainerPort);
	profile.setProperty(Profile.MAIN, Boolean.FALSE.toString());
	profile.setProperty(Profile.JVM, Profile.ANDROID);

	if (AndroidHelper.isEmulator())
	{
	    // Emulator: this is needed to work with emulated devices
	    profile.setProperty(Profile.LOCAL_HOST, AndroidHelper.LOOPBACK);
	} else
	{
	    _log.info(Profile.LOCAL_HOST + " = " + localIpAddress);
	    profile.setProperty(Profile.LOCAL_HOST, localIpAddress);
	}

	// // Emulator: this is not really needed on a real device
	// profile.setProperty(Profile.LOCAL_PORT, "2000");
	_log.debug("buildJadeProfile() DONE");
	return profile;
    }

    protected void startJadePlatform(final Activity activity,
	    final RuntimeCallback<AgentController> agentStartupCallback,
	    final String gmail, final Object model)
    {
	_log.debug("startJadePlatform() ...");
	_log.debug("startJadePlatform() : buildJadeProfile()");

	final Properties profile = buildJadeProfile(activity);

	if (_microRuntimeServiceBinder == null)
	{
	    _serviceConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className,
			IBinder service)
		{
		    _log.debug("startJadePlatform() : ServiceConnection.onServiceConnected()");
		    _microRuntimeServiceBinder = (MicroRuntimeServiceBinder) service;
		    _log.debug("startJadePlatform() : Gateway successfully bound to MicroRuntimeService");
		    startContainer(activity, profile, agentStartupCallback,
			    gmail, model);
		};

		public void onServiceDisconnected(ComponentName className)
		{
		    _log.debug("startJadePlatform() : ServiceConnection.onServiceDisconnected()");
		    _microRuntimeServiceBinder = null;
		    _log.debug("startJadePlatform() : Gateway unbound from MicroRuntimeService");
		};
	    };

	    _log.debug("startJadePlatform() : Binding Gateway to MicroRuntimeService...");
	    activity.bindService(new Intent(activity.getApplicationContext(),
		    MicroRuntimeService.class), _serviceConnection,
		    Context.BIND_AUTO_CREATE);
	} else
	{
	    _log.debug("startJadePlatform() : MicroRuntimeGateway already binded to service");
	    startContainer(activity, profile, agentStartupCallback, gmail,
		    model);
	}
    }

    private void startContainer(final Activity activity,
	    final Properties profile,
	    final RuntimeCallback<AgentController> agentStartupCallback,
	    final String gmail, final Object model)
    {
	_log.debug("startContainer() ...");
	if (!MicroRuntime.isRunning())
	{
	    _log.debug("startContainer() : MicroRuntime is not running");
	    _log.debug("startContainer() : _microRuntimeServiceBinder.startAgentContainer()");
	    _microRuntimeServiceBinder.startAgentContainer(profile,
		    new RuntimeCallback<Void>() {
			@Override
			public void onSuccess(Void thisIsNull)
			{
			    _log.debug("startContainer() : Successfully start of the container");
			    startAllAgents(activity, agentStartupCallback,
				    gmail, model);
			}

			@Override
			public void onFailure(Throwable throwable)
			{
			    _log.error(
				    "startContainer() : Failed to start the container...\n{}",
				    throwable.getMessage());
			}
		    });
	} else
	{
	    _log.debug("startContainer() : MicroRuntime is already running");
	    startAllAgents(activity, agentStartupCallback, gmail, model);
	}
    }

    private void startAllAgents(final Activity activity,
	    final RuntimeCallback<AgentController> agentStartupCallback,
	    String gmail, Object model)
    {
	_log.debug("startAllAgents() ...");
	for (Entry<String, Class<? extends Agent>> entry : agentClassMap
		.entrySet())
	{
	    String agentName;
	    // pas besoin de l'adresse gmail pour le nom des agents de l'hote
	    if (gmail != null && !gmail.isEmpty())
	    {
		agentName = entry.getKey() + "-" + gmail;
	    } else
	    {
		agentName = entry.getKey();
	    }
	    _log.debug("startAllAgents() : startAgent ({})", entry.getKey()
		    + agentName);
	    startOneAgent(entry.getValue(), agentName, activity,
		    agentStartupCallback, model, gmail);
	}
	_log.debug("startAllAgents() DONE");
    }

    private void startOneAgent(final Class<? extends Agent> agentClass,
	    final String nickName, final Activity activity,
	    final RuntimeCallback<AgentController> agentStartupCallback,
	    Object model, String gmail)
    {
	_log.debug("startOneAgent() ...");
	_microRuntimeServiceBinder
		.startAgent(nickName, agentClass.getName(), new Object[] {
			activity.getApplicationContext(), model, gmail },
			new RuntimeCallback<Void>() {

			    @Override
			    public void onSuccess(Void thisIsNull)
			    {
				_log.debug("startOneAgent : Successfully start of the "
					+ agentClass.getName() + "...");
				readyAgents++;

				try
				{
				    _log.debug("startOneAgent : call callback.onSuccess");
				    agentStartupCallback.onSuccess(MicroRuntime
					    .getAgent(nickName));
				} catch (ControllerException e)
				{
				    // Should never happen
				    _log.error("startOneAgent : call callback.onFailure");
				    agentStartupCallback.onFailure(e);
				}
			    }

			    @Override
			    public void onFailure(Throwable throwable)
			    {
				_log.error("Failed to start the "
					+ agentClass.getName() + "...");
				agentStartupCallback.onFailure(throwable);
			    }
			});
	_log.debug("startOneAgent DONE");
    }

    public void stopAgentContainer()
    {
	_log.debug("stopAgentContainer() ...");

	if (_microRuntimeServiceBinder != null)
	{
	    _microRuntimeServiceBinder
		    .stopAgentContainer(new RuntimeCallback<Void>() {
			@Override
			public void onSuccess(Void thisIsNull)
			{
			    _log.debug("stopAgentContainer() : Successful");
			}

			@Override
			public void onFailure(Throwable throwable)
			{
			    _log.error("Failed to stop the AgentContainer...");
			}
		    });
	}
    }

    public <T> T getAgent(Activity activity, String localName, Class<T> interfac)
    {
	T agent = null;
	try
	{
	    agent = MicroRuntime.getAgent(localName).getO2AInterface(interfac);
	    _log.debug("{} loaded !", localName);
	} catch (StaleProxyException e)
	{
	    Utils.showAlertDialog(activity,
		    activity.getString(R.string.msg_interface_exc), true);
	} catch (ControllerException e)
	{
	    Utils.showAlertDialog(activity,
		    activity.getString(R.string.msg_controller_exc), true);
	}
	return agent;

    }
}
