package com.utc.cards.table.jade.agent.hostAgent;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import com.utc.cards.model.HostModel;
import com.utc.cards.model.game.GameStatus;

/**
 * les demandes sont traitées seulement si les inscriptions sont ouvertes
 * 
 */
public class SubscriptionBehaviour extends CyclicBehaviour
{

    private static final long serialVersionUID = -1033764098211298884L;

    private MessageTemplate subscriptionTemplate = MessageTemplate
	    .MatchPerformative(ACLMessage.SUBSCRIBE);
    private HostAgent agent;

    private HostModel model;

    public SubscriptionBehaviour(HostAgent hostAgent)
    {
	this.agent = hostAgent;
	this.model = agent.getModel();
    }

    @Override
    public void action()
    {
	ACLMessage message = agent.receive(subscriptionTemplate);
	if (message != null)
	{
	    ACLMessage reply = message.createReply();
	    if (subscriptionIsOpen() && !gameIsFull())
	    {
		reply.setPerformative(ACLMessage.AGREE);

	    } else
	    {
		reply.setPerformative(ACLMessage.REFUSE);
	    }
	    agent.send(message);
	}
	block();
    }

    private boolean subscriptionIsOpen()
    {
	return model.getGame() != null
		&& model.getGame().getStatus() == GameStatus.SUBSCRIPTION;
    }

    private boolean gameIsFull()
    {
	return model.getGame() != null
		&& model.getGame().getPlayers().size() >= model.getGame()
			.getMaxPlayerCount();
    }
}
