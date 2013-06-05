package com.utc.cards.player.jade.agent.playerHelperAgent;

import jade.core.Agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.Context;

import com.utc.cards.model.PlayerModel;

public class PlayerHelperAgent extends Agent implements IPlayerHelperAgent
{
    private static final long serialVersionUID = 1293413133000240866L;
    private static Logger log = LoggerFactory
	    .getLogger(PlayerHelperAgent.class);

    private PlayerModel model;
    private Context context;

    @Override
    protected void setup()
    {
	super.setup();
	Object[] args = getArguments();
	if (args != null && args.length > 0)
	{
	    if (args[0] instanceof Context)
	    {
		context = (Context) args[0];
	    } else
	    {
		log.error("Missing Context arg during agent setup");
	    }
	    if (args[1] instanceof PlayerModel)
	    {
		model = (PlayerModel) args[1];
	    } else
	    {
		log.error("Missing PlayerModel arg during agent setup");
	    }
	}

	//
	// // Add initial behaviours
	// addBehaviour(new ...Behaviour(this));

	// // Initialize the message used to convey spoken sentences
	// spokenMsg = new ACLMessage(ACLMessage.INFORM);
	// spokenMsg.setConversationId(CHAT_ID);

	// expose l'interface pour la rendre accessible par les activity
	registerO2AInterface(IPlayerHelperAgent.class, this);
    }

    @Override
    public void askAdvice()
    {
	// TODO Auto-generated method stub

    }

}
