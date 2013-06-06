package com.utc.cards.table.jade.agent.hostAgent;

import jade.core.Agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.Context;

import com.utc.cards.model.HostModel;
//import jade.core.AID;
//import jade.domain.DFService;
//import jade.domain.FIPAException;
//import jade.domain.FIPAAgentManagement.DFAgentDescription;
//import jade.domain.FIPAAgentManagement.ServiceDescription;

public class HostAgent extends Agent implements IHostAgent
{

    private static final long serialVersionUID = -8649418473966240098L;
    private static Logger log = LoggerFactory.getLogger(HostAgent.class);

    private HostModel model;
    private Context context;

    @Override
    protected void setup()
    {
	super.setup();
	Object[] args = getArguments();
	if (args != null && args.length > 0)
	{
	    // try catch for host test only, we don't have access to Context
	    // class in the Cards-host project since it runs in windows
	    try
	    {
		if (args[0] instanceof Context)
		{
		    context = (Context) args[0];
		} else
		{
		    log.error("Missing Context arg during agent setup");
		}
		if (args[1] instanceof HostModel)
		{
		    model = (HostModel) args[1];
		} else
		{
		    log.error("Missing HostModel arg during agent setup");
		}
	    } catch (Exception e)
	    {
		// e.printStackTrace();
	    }
	}

	// écoute la plupart des demandes des playerAgent
	// addBehaviour(new HostListenerBehaviour(this));

	// Behaviour d'inscription au jeu
	log.debug("addBehaviour : SubscriptionResponder");
	// addBehaviour(new SubscriptionResponder(this, sTemplate, this));
	addBehaviour(new SubscriptionBehaviour(this));

	// expose l'interface pour la rendre accessible par les activity
	registerO2AInterface(IHostAgent.class, this);
    }

    @Override
    public void sendGameSelected()
    {
	// TODO Auto-generated method stub

    }

    @Override
    public void createIAPlayer()
    {
	// TODO Auto-generated method stub

    }

    @Override
    public void removeIAPlayer()
    {
	// TODO Auto-generated method stub

    }

    public Context getContext()
    {
	return context;
    }

    public HostModel getModel()
    {
	return model;
    }

}
