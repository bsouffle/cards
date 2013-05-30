package com.utc.cards.common.jade;

import jade.android.MicroRuntimeService;
import jade.android.MicroRuntimeServiceBinder;
import jade.android.RuntimeCallback;
import jade.core.Agent;
import jade.core.MicroRuntime;
import jade.util.leap.Properties;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

public abstract class AbstractAgentManager 
{
	
	//commit from git
	private static Logger _log = LoggerFactory.getLogger(AbstractAgentManager.class);

	private static MicroRuntimeServiceBinder _microRuntimeServiceBinder;
	private static ServiceConnection _serviceConnection;

	protected static AbstractAgentManager _instance;
	private static int _readyAgents = 0;

	public abstract Properties buildJadeProfile(String host, String port);

	protected void startAgents
		(
			final Activity activity,
			final RuntimeCallback<AgentController> agentStartupCallback,
			final Class[] clazzs,
			String host, 
			String port 
		)
	{
		
		final Properties profile = buildJadeProfile(host, port);
		
		if (_microRuntimeServiceBinder == null) 
		{
			_serviceConnection = new ServiceConnection() 
			{
				public void onServiceConnected(ComponentName className, IBinder service) 
				{
					_microRuntimeServiceBinder = (MicroRuntimeServiceBinder) service;
					_log.info("Gateway successfully bound to MicroRuntimeService");
					startContainer(activity, profile, agentStartupCallback, clazzs);
				};

				public void onServiceDisconnected(ComponentName className) 
				{
					_microRuntimeServiceBinder = null;
					_log.info("Gateway unbound from MicroRuntimeService");
				};
			};
			
			_log.info("Binding Gateway to MicroRuntimeService...");
			
			activity.bindService(new Intent(activity.getApplicationContext(), MicroRuntimeService.class), _serviceConnection, Context.BIND_AUTO_CREATE);
		} 
		else 
		{
			_log.info("MicroRuntimeGateway already binded to service");
			startContainer(activity, profile, agentStartupCallback, clazzs);
		}
	}

	protected void startAgent
		(
			final Class<? extends Agent> agentClass,
			final Activity activity,
			final RuntimeCallback<AgentController> agentStartupCallback
		)
	{

		_microRuntimeServiceBinder.startAgent(
				agentClass.getSimpleName(),
				agentClass.getName(),
				new Object[] { activity.getApplicationContext() },
				new RuntimeCallback<Void>() {

					@Override
					public void onSuccess(Void thisIsNull) 
					{
						_log.info("Successfully start of the " + agentClass.getName() + "...");
						_readyAgents++;

						try 
						{
							agentStartupCallback.onSuccess(MicroRuntime.getAgent(agentClass.getSimpleName()));
						} 
						catch (ControllerException e) 
						{
							// Should never happen
							agentStartupCallback.onFailure(e);
						}
					}

					@Override
					public void onFailure(Throwable throwable) 
					{
						_log.error("Failed to start the " + agentClass.getName() + "...");
						
						agentStartupCallback.onFailure(throwable);
					}
				});
	}

	@SuppressWarnings("unchecked")
	private void startContainer
		(
			final Activity activity,
			final Properties profile,
			final RuntimeCallback<AgentController> agentStartupCallback,
			final Class[] clazzs
		)
	{
		if (!MicroRuntime.isRunning()) 
		{
			_microRuntimeServiceBinder.startAgentContainer(
					profile,
					new RuntimeCallback<Void>() 
					{
						@Override
						public void onSuccess(Void thisIsNull) 
						{
							_log.info("Successfully start of the container...");
							for (Class<? extends Agent> agentClass : clazzs) 
							{
								startAgent(agentClass, activity, agentStartupCallback);
							}
						}

						@Override
						public void onFailure(Throwable throwable) 
						{
							_log.error("Failed to start the container...");
						}
					});
		} 
		else 
		{
			for (Class<? extends Agent> agentClass : clazzs) 
			{
				startAgent(agentClass, activity, agentStartupCallback);
			}
		}
	}
}
