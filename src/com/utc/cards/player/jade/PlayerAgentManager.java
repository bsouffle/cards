package com.utc.cards.player.jade;

import static com.utc.cards.Constants.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.utc.cards.common.jade.AbstractAgentManager;
import com.utc.cards.player.jade.agent.playerAgent.PlayerAgent;
import com.utc.cards.player.jade.agent.playerHelperAgent.PlayerHelperAgent;
import com.utc.cards.table.jade.agent.gameAgent.GameAgent;

import jade.android.AndroidHelper;
import jade.android.RuntimeCallback;
import jade.core.Profile;
import jade.util.leap.Properties;
import jade.wrapper.AgentController;

public class PlayerAgentManager extends AbstractAgentManager
{

    private static Logger _log = LoggerFactory
	    .getLogger(PlayerAgentManager.class);

    @SuppressWarnings("rawtypes")
    private static final Class[] PLAYER_AGENTS_CLASSSES = { PlayerAgent.class,
	    PlayerHelperAgent.class, GameAgent.class };

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
    }

    public void startAgents(final Activity activity,
	    final AgentActivityListener agentActivityListener)
    {
	final RuntimeCallback<AgentController> agentStartupCallback = new RuntimeCallback<AgentController>() {

	    @Override
	    public void onSuccess(AgentController agent)
	    {
		_log.debug("startAgents() : {}/{}", _readyAgents,
			PLAYER_AGENTS_CLASSSES.length);
		if (_readyAgents == PLAYER_AGENTS_CLASSSES.length)
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
	startJadePlatformForPlayer(activity, agentStartupCallback,
		PLAYER_AGENTS_CLASSSES);
	_log.debug("startAgents() DONE");
    }

    @Override
    public Properties buildJadeProfile(Activity activity)
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

	// Emulator: this is not really needed on a real device
	profile.setProperty(Profile.LOCAL_PORT, "2000");
	_log.debug("buildJadeProfile() DONE");
	return profile;
    }

}
