package com.utc.cards.player.jade.agent.playerAgent;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class PlayerListenerBehaviour extends CyclicBehaviour
{

    private static final long serialVersionUID = 5312576538932622504L;

    public PlayerListenerBehaviour(PlayerAgent playerAgent)
    {
	super(playerAgent);
    }

    // private MessageTemplate template = MessageTemplate
    // .MatchConversationId(CARDS_ID);

    public void action()
    {
	// ACLMessage msg = myAgent.receive(template);
	ACLMessage msg = myAgent.receive();
	if (msg != null)
	{
	    if (msg.getPerformative() == ACLMessage.INFORM)
	    {
		// notifySpoken(msg.getSender().getLocalName(),
		// msg.getContent());
	    } else
	    {
		// handleUnexpected(msg);
	    }
	} else
	{
	    block();
	}
    }

}
