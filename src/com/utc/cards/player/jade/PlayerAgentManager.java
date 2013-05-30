package com.utc.cards.player.jade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.content.SharedPreferences;

import com.utc.cards.common.jade.AbstractAgentManager;
import com.utc.cards.player.jade.agent.HelpAgent;
import com.utc.cards.player.jade.agent.PlayCheckerAgent;
import com.utc.cards.player.jade.agent.PlayerAgent;

import jade.android.AndroidHelper;
import jade.android.RuntimeCallback;
import jade.core.Profile;
import jade.util.leap.Properties;
import jade.wrapper.AgentController;

public class PlayerAgentManager extends AbstractAgentManager {
	
	private static Logger _log = LoggerFactory.getLogger(PlayerAgentManager.class);

	private static int _readyAgents = 0;

	@SuppressWarnings("rawtypes")
	private static final Class[] PLAYER_AGENTS_CLASSSES = {
			PlayerAgent.class, 
			HelpAgent.class,
			PlayCheckerAgent.class 
	};

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

	public void startAgents(final Activity activity, final AgentActivityListener agentActivityListener) 
	{

		final RuntimeCallback<AgentController> agentStartupCallback = new RuntimeCallback<AgentController>() 
		{

			@Override
			public void onSuccess(AgentController agent) 
			{
				if (_readyAgents == PLAYER_AGENTS_CLASSSES.length) 
				{
					agentActivityListener.onAllAgentsReady();
				}
			}

			@Override
			public void onFailure(Throwable throwable) 
			{
				_log.warn("Nickname already in use!");
				// myHandler.postError(getString(R.string.msg_nickname_in_use));
			}
		};
		
		SharedPreferences settings = activity.getSharedPreferences("jadeChatPrefsFile", 0);
		String host = settings.getString("defaultHost", "");
		String port = settings.getString("defaultPort", "");
		startAgents(activity, agentStartupCallback, PLAYER_AGENTS_CLASSSES, host, port);
	}

	@Override
	public Properties buildJadeProfile(final String host, final String port) 
	{
		final Properties profile = new Properties();
		profile.setProperty(Profile.MAIN_HOST, host);
		profile.setProperty(Profile.MAIN_PORT, port);
		profile.setProperty(Profile.MAIN, Boolean.FALSE.toString());
		profile.setProperty(Profile.JVM, Profile.ANDROID);

		if (AndroidHelper.isEmulator()) 
		{
			// Emulator: this is needed to work with emulated devices
			profile.setProperty(Profile.LOCAL_HOST, AndroidHelper.LOOPBACK);
		} 
		else 
		{
			profile.setProperty(Profile.LOCAL_HOST, AndroidHelper.getLocalIPAddress());
		}
		
		// Emulator: this is not really needed on a real device
		profile.setProperty(Profile.LOCAL_PORT, "2000");
		return profile;
	}

}
