package com.utc.cards.table.jade.agent;

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

import com.utc.cards.Constants;
import com.utc.cards.R;
import com.utc.cards.model.HostModel;
import com.utc.cards.player.jade.AgentActivityListener;
import com.utc.cards.table.jade.agent.gameAgent.GameAgent;
import com.utc.cards.table.jade.agent.hostAgent.HostAgent;
import com.utc.cards.table.jade.agent.rulesAgent.RulesAgent;
import com.utc.cards.table.view.TableLaunchGameActivity;
import com.utc.cards.utils.Utils;

public class HostAgentManager
{

    private static Logger _log = LoggerFactory
	    .getLogger(HostAgentManager.class);
    // VERSION MAIN CONTAINER
    //
    // private RuntimeServiceBinder _runtimeServiceBinder;
    // private AgentContainerHandler containerHandler;
    private MicroRuntimeServiceBinder _microRuntimeServiceBinder;

    private ServiceConnection _serviceConnection;

    private Map<String, Class<? extends Agent>> agentClassMap = new HashMap<String, Class<? extends Agent>>();

    private static HostAgentManager _instance;
    private int readyAgents = 0;

    public static HostAgentManager instance()
    {
	if (_instance == null)
	{
	    _instance = new HostAgentManager();
	}
	return _instance;
    }

    private HostAgentManager()
    {
	super();
	agentClassMap.put(Constants.CARDS_HOST_AGENT_NAME, HostAgent.class);
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
		new HostModel());
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

    // VERSION MAIN CONTAINER
    //
    // // seule méthode visible, utilisée pour démarrer tous les agents du
    // player
    // public void startAgents(final Activity activity,
    // final AgentActivityListener agentActivityListener)
    // {
    // readyAgents = 0;
    // final RuntimeCallback<AgentController> agentStartupCallback = new
    // RuntimeCallback<AgentController>() {
    //
    // @Override
    // public void onSuccess(AgentController agent)
    // {
    // _log.debug("startAgents() : {}/{}", readyAgents,
    // agentClassMap.size());
    // if (readyAgents == agentClassMap.size())
    // {
    // _log.debug("agentStartupCallback.onSuccess() : all agents ready !");
    // agentActivityListener.onAllAgentsReady();
    // }
    // }
    //
    // @Override
    // public void onFailure(Throwable throwable)
    // {
    // _log.warn("agentStartupCallback.onFailure() : Failed to start at least one agent");
    // }
    // };
    // _log.debug("startAgents() ...");
    //
    // startJadePlatform(activity, agentStartupCallback, null, new HostModel());
    // _log.debug("startAgents() DONE");
    // }

    // protected Properties buildJadeProfile(Activity activity)
    // {
    // _log.debug("buildJadeProfile() ...");
    // SharedPreferences settings = activity.getSharedPreferences(
    // JADE_CARDS_PREFS_FILE, Context.MODE_PRIVATE);
    // String localIpAddress = settings.getString(LOCAL_IP, "");
    // // String mainContainerHost = settings.getString(HOST_IP, "");
    // String mainContainerPort = settings.getString(HOST_PORT, "");
    // // HOST version
    // final Properties profile = new Properties();
    // _log.info("JADE properties :");
    // // _log.info(Profile.MAIN_HOST + " = " + mainContainerHost);
    // _log.info(Profile.MAIN_HOST + " = " + localIpAddress);
    // _log.info(Profile.MAIN_PORT + " = " + mainContainerPort);
    // _log.info(Profile.MAIN + " = " + Boolean.TRUE.toString());
    // _log.info(Profile.JVM + " = " + Profile.ANDROID);
    // // profile.setProperty(Profile.MAIN_HOST, mainContainerHost);
    // // profile.setProperty(Profile.MAIN_PORT, mainContainerPort);
    // profile.setProperty(Profile.MAIN, Boolean.TRUE.toString());
    // profile.setProperty(Profile.JVM, Profile.ANDROID);
    //
    // // if (AndroidHelper.isEmulator())
    // // {
    // // // Emulator: this is needed to work with emulated devices
    // // profile.setProperty(Profile.LOCAL_HOST, AndroidHelper.LOOPBACK);
    // // } else
    // // {
    // // _log.info(Profile.LOCAL_HOST + " = " + localIpAddress);
    // // profile.setProperty(Profile.LOCAL_HOST, localIpAddress);
    // // }
    //
    // // Emulator: this is not really needed on a real device
    // profile.setProperty(Profile.LOCAL_PORT, "2000");
    // _log.debug("buildJadeProfile() DONE");
    // return profile;
    // }
    //
    // protected void startJadePlatform(final Activity activity,
    // final RuntimeCallback<AgentController> agentStartupCallback,
    // final String gmail, final Object model)
    // {
    // _log.debug("startJadePlatform() ...");
    // _log.debug("startJadePlatform() : buildJadeProfile()");
    //
    // final Properties profile = buildJadeProfile(activity);
    //
    // if (_runtimeServiceBinder == null)
    // {
    // _log.debug("startJadePlatform() : new ServiceConnection()");
    // _serviceConnection = new ServiceConnection() {
    // public void onServiceConnected(ComponentName className,
    // IBinder service)
    // {
    // _log.debug("startJadePlatform() : ServiceConnection.onServiceConnected()");
    // _runtimeServiceBinder = (RuntimeServiceBinder) service;
    // _log.debug("startJadePlatform() : Gateway successfully bound to RuntimeService");
    // startContainer(activity, profile, agentStartupCallback,
    // gmail, model);
    // };
    //
    // public void onServiceDisconnected(ComponentName className)
    // {
    // _log.debug("startJadePlatform() : ServiceConnection.onServiceDisconnected()");
    // _runtimeServiceBinder = null;
    // _log.debug("startJadePlatform() : Gateway unbound from RuntimeService");
    // };
    // };
    //
    // _log.debug("startJadePlatform() : Binding Gateway to MicroRuntimeService...");
    // activity.bindService(new Intent(activity.getApplicationContext(),
    // RuntimeService.class), _serviceConnection,
    // Context.BIND_AUTO_CREATE);
    // } else
    // {
    // _log.debug("startJadePlatform() : RuntimeGateway already binded to service");
    // startContainer(activity, profile, agentStartupCallback, gmail,
    // model);
    // }
    // }
    //
    // private void startContainer(final Activity activity,
    // final Properties profile,
    // final RuntimeCallback<AgentController> agentStartupCallback,
    // final String gmail, final Object model)
    // {
    // _log.debug("startContainer() ...");
    // if (HostAgentManager.this.containerHandler == null)
    // {
    // _log.debug("startContainer() : containerHandler is not running");
    // _log.debug("startContainer() : _runtimeServiceBinder.createMainAgentContainer()");
    // _runtimeServiceBinder
    // .createMainAgentContainer(new RuntimeCallback<AgentContainerHandler>() {
    //
    // @Override
    // public void onSuccess(
    // AgentContainerHandler containerHandler)
    // {
    // _log.debug("createMainAgentContainer() : Successfully start of the container");
    // HostAgentManager.this.containerHandler = containerHandler;
    // startAllAgents(activity, agentStartupCallback,
    // model);
    // }
    //
    // @Override
    // public void onFailure(Throwable throwable)
    // {
    // _log.error(
    // "createMainAgentContainer() : Failed to start the container...\n{}",
    // throwable.getMessage());
    // }
    // });
    //
    // } else
    // {
    // _log.debug("startContainer() : containerHandler is already running");
    // startAllAgents(activity, agentStartupCallback, model);
    // }
    // }
    //
    // private void startAllAgents(final Activity activity,
    // final RuntimeCallback<AgentController> agentStartupCallback,
    // Object model)
    // {
    // _log.debug("startAllAgents() ...");
    // for (Entry<String, Class<? extends Agent>> entry : agentClassMap
    // .entrySet())
    // {
    // String agentName = entry.getKey();
    // _log.debug("startAllAgents() : startAgent ({})", entry.getKey()
    // + agentName);
    // startOneAgent(entry.getValue(), agentName, activity,
    // agentStartupCallback, model);
    // }
    // _log.debug("startAllAgents() DONE");
    // }
    //
    // private void startOneAgent(final Class<? extends Agent> agentClass,
    // final String nickName, final Activity activity,
    // final RuntimeCallback<AgentController> agentStartupCallback,
    // Object model)
    // {
    // _log.debug("startOneAgent() ...");
    //
    // containerHandler.createNewAgent(nickName, agentClass.getName(),
    // new Object[] { activity.getApplicationContext(), model },
    // new RuntimeCallback<AgentHandler>() {
    //
    // @Override
    // public void onSuccess(AgentHandler arg0)
    // {
    // _log.debug("startOneAgent : Successfully start of the "
    // + agentClass.getName() + "...");
    // readyAgents++;
    // _log.debug("startOneAgent : call callback.onSuccess");
    // agentStartupCallback.onSuccess(arg0
    // .getAgentController());
    // }
    //
    // @Override
    // public void onFailure(Throwable throwable)
    // {
    // _log.error("Failed to start the "
    // + agentClass.getName() + "...");
    // agentStartupCallback.onFailure(throwable);
    //
    // }
    // });
    //
    // _log.debug("startOneAgent DONE");
    // }
    //
    // public void stopAgentContainer()
    // {
    // _log.debug("stopAgentContainer() ...");
    //
    // if (_runtimeServiceBinder != null)
    // {
    // try
    // {
    // containerHandler.getAgentContainer().kill();
    // } catch (StaleProxyException e)
    // {
    // _log.error("Failed to stop the AgentContainer...");
    // e.printStackTrace();
    // }
    // // containerHandler.kill(new RuntimeCallback<Void>() {
    // // @Override
    // // public void onSuccess(Void thisIsNull)
    // // {
    // // _log.debug("stopAgentContainer() : Successful");
    // // containerHandler = null;
    // // }
    // //
    // // @Override
    // // public void onFailure(Throwable throwable)
    // // {
    // // _log.error("Failed to stop the AgentContainer...");
    // // }
    // // });
    // }
    // }

    public void startGameAndRulesAgent(TableLaunchGameActivity activity,
	    HostModel model, final AgentActivityListener agentActivityListener)
    {
	final HashMap<Class<? extends Agent>, String> map = new HashMap<Class<? extends Agent>, String>();
	map.put(GameAgent.class, Constants.CARDS_HOST_GAME_AGENT_NAME);
	map.put(RulesAgent.class, Constants.CARDS_HOST_RULES_AGENT_NAME);
	for (final Entry<Class<? extends Agent>, String> entry : map.entrySet())
	{
	    startOneAgent(entry.getKey(), entry.getValue(), activity,
		    new RuntimeCallback<AgentController>() {
			int count = 0;

			@Override
			public void onSuccess(AgentController arg0)
			{
			    _log.debug("startOneAgent : Successfully start of the "
				    + entry.getKey().getName() + "...");
			    count++;
			    if (count == map.size())
			    {
				agentActivityListener.onAllAgentsReady();
			    }
			}

			@Override
			public void onFailure(Throwable arg0)
			{
			    _log.error("Failed to start the "
				    + entry.getKey().getName() + "...");
			}
		    }, model, "");
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

    // VERSION MAIN CONTAINER
    //
    // public void startGameAndRulesAgent(TableLaunchGameActivity activity,
    // HostModel model, final AgentActivityListener agentActivityListener)
    // {
    // final HashMap<Class<?>, String> map = new HashMap<Class<?>, String>();
    // map.put(GameAgent.class, Constants.CARDS_HOST_GAME_AGENT_NAME);
    // map.put(RulesAgent.class, Constants.CARDS_HOST_RULES_AGENT_NAME);
    // for (final Entry<Class<?>, String> entry : map.entrySet())
    // {
    // containerHandler.createNewAgent(entry.getValue(), entry.getKey()
    // .getName(), new Object[] {
    // activity.getApplicationContext(), model },
    // new RuntimeCallback<AgentHandler>() {
    // int count = 0;
    //
    // @Override
    // public void onSuccess(AgentHandler agentHandler)
    // {
    // _log.debug("startOneAgent : Successfully start of the "
    // + entry.getKey().getName() + "...");
    // count++;
    // if (count == map.size())
    // {
    // agentActivityListener.onAllAgentsReady();
    // }
    // }
    //
    // @Override
    // public void onFailure(Throwable throwable)
    // {
    // _log.error("Failed to start the "
    // + entry.getKey().getName() + "...");
    // }
    // });
    //
    // }
    //
    // }
    //
    // public <T> T getAgent(String localName, Class<T> interfac)
    // {
    // try
    // {
    // return containerHandler.getAgentContainer().getAgent(localName)
    // .getO2AInterface(interfac);
    // } catch (StaleProxyException e)
    // {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // } catch (ControllerException e)
    // {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // return null;
    // }
}
