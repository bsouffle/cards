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

    private static Logger _log = LoggerFactory
	    .getLogger(AbstractAgentManager.class);

    private static MicroRuntimeServiceBinder _microRuntimeServiceBinder;
    private static ServiceConnection _serviceConnection;

    protected static AbstractAgentManager _instance;
    protected static int _readyAgents = 0;

    public abstract Properties buildJadeProfile(Activity activity);

    @SuppressWarnings("rawtypes")
    protected void startJadePlatformForPlayer(final Activity activity,
	    final RuntimeCallback<AgentController> agentStartupCallback,
	    final Class[] clazzs)
    {
	_log.debug("startJadePlatformForPlayer() ...");
	_log.debug("startJadePlatformForPlayer() : buildJadeProfile()");

	final Properties profile = buildJadeProfile(activity);

	if (_microRuntimeServiceBinder == null)
	{
	    _serviceConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className,
			IBinder service)
		{
		    _log.debug("startJadePlatformForPlayer() : ServiceConnection.onServiceConnected()");
		    _microRuntimeServiceBinder = (MicroRuntimeServiceBinder) service;
		    _log.debug("startJadePlatformForPlayer() : Gateway successfully bound to MicroRuntimeService");
		    startContainer(activity, profile, agentStartupCallback,
			    clazzs);
		};

		public void onServiceDisconnected(ComponentName className)
		{
		    _log.debug("startJadePlatformForPlayer() : ServiceConnection.onServiceDisconnected()");
		    _microRuntimeServiceBinder = null;
		    _log.debug("startJadePlatformForPlayer() : Gateway unbound from MicroRuntimeService");
		};
	    };

	    _log.debug("startJadePlatformForPlayer() : Binding Gateway to MicroRuntimeService...");
	    activity.bindService(new Intent(activity.getApplicationContext(),
		    MicroRuntimeService.class), _serviceConnection,
		    Context.BIND_AUTO_CREATE);
	} else
	{
	    _log.debug("startJadePlatformForPlayer() : MicroRuntimeGateway already binded to service");
	    startContainer(activity, profile, agentStartupCallback, clazzs);
	}
    }

    @SuppressWarnings({ "rawtypes" })
    private void startContainer(final Activity activity,
	    final Properties profile,
	    final RuntimeCallback<AgentController> agentStartupCallback,
	    final Class[] clazzs)
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
				    clazzs);
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
	    startAllAgents(activity, agentStartupCallback, clazzs);
	}
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void startAllAgents(final Activity activity,
	    final RuntimeCallback<AgentController> agentStartupCallback,
	    final Class[] clazzs)
    {
	_log.debug("startAllAgents() ...");
	for (Class<? extends Agent> agentClass : clazzs)
	{
	    _log.debug("startAllAgents() : startAgent ({})",
		    agentClass.getName());
	    startOneAgent(agentClass, activity, agentStartupCallback);
	}
	_log.debug("startAllAgents() DONE");
    }

    private void startOneAgent(final Class<? extends Agent> agentClass,
	    final Activity activity,
	    final RuntimeCallback<AgentController> agentStartupCallback)
    {
	_log.debug("startOneAgent() ...");
	_microRuntimeServiceBinder.startAgent(agentClass.getSimpleName(),
		agentClass.getName(),
		new Object[] { activity.getApplicationContext() },
		new RuntimeCallback<Void>() {

		    @Override
		    public void onSuccess(Void thisIsNull)
		    {
			_log.debug("startOneAgent : Successfully start of the "
				+ agentClass.getName() + "...");
			_readyAgents++;

			try
			{
			    _log.debug("startOneAgent : call callback.onSuccess");
			    agentStartupCallback.onSuccess(MicroRuntime
				    .getAgent(agentClass.getSimpleName()));
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
