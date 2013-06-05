package com.utc.cards.table.jade.agent.rulesAgent;

import jade.core.Agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.Context;

import com.utc.cards.model.HostModel;
import com.utc.cards.model.deck.Deck;
import com.utc.cards.model.player.IPlayer;

public class RulesAgent extends Agent implements IRulesAgent
{
    private static final long serialVersionUID = -2248519337273621061L;
    private static Logger log = LoggerFactory.getLogger(RulesAgent.class);

    private HostModel model;
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
	    if (args[1] instanceof HostModel)
	    {
		model = (HostModel) args[1];
	    } else
	    {
		log.error("Missing HostModel arg during agent setup");
	    }
	}
	//
	// // Add initial behaviours
	// addBehaviour(new ...Behaviour(this));

	// // Initialize the message used to convey spoken sentences
	// spokenMsg = new ACLMessage(ACLMessage.INFORM);
	// spokenMsg.setConversationId(CHAT_ID);

	// expose l'interface pour la rendre accessible par les activity
	registerO2AInterface(IRulesAgent.class, this);
    }

    @Override
    public void sendInitialCards()
    {
	// TODO Auto-generated method stub

    }

    @Override
    public void validatePlayerCards(Deck cards)
    {
	// TODO Auto-generated method stub

    }

    @Override
    public void sendAdvice(IPlayer player)
    {
	// TODO Auto-generated method stub

    }

}
