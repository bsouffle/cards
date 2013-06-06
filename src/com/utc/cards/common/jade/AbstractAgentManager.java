package com.utc.cards.common.jade;

import jade.android.MicroRuntimeService;
import jade.android.MicroRuntimeServiceBinder;
import jade.android.RuntimeCallback;
import jade.core.Agent;
import jade.core.MicroRuntime;
import jade.util.leap.Properties;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

import java.util.Map;
import java.util.Map.Entry;

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

    private MicroRuntimeServiceBinder _microRuntimeServiceBinder;
    private ServiceConnection _serviceConnection;

    protected static AbstractAgentManager _instance;
    protected int readyAgents = 0;

    protected abstract Properties buildJadeProfile(Activity activity);

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
	for (Entry<String, Class<? extends Agent>> entry : getStartupAgentClasses()
		.entrySet())
	{
	    String agentName = entry.getKey() + "-" + gmail;
	    _log.debug("startAllAgents() : startAgent ({})", entry.getKey()
		    + agentName);
	    startOneAgent(entry.getValue(), agentName, activity,
		    agentStartupCallback, model);
	}
	_log.debug("startAllAgents() DONE");
    }

    protected abstract Map<String, Class<? extends Agent>> getStartupAgentClasses();

    private void startOneAgent(final Class<? extends Agent> agentClass,
	    final String nickName, final Activity activity,
	    final RuntimeCallback<AgentController> agentStartupCallback,
	    Object model)
    {
	_log.debug("startOneAgent() ...");
	_microRuntimeServiceBinder.startAgent(nickName, agentClass.getName(),
		new Object[] { activity.getApplicationContext(), model },
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

}
