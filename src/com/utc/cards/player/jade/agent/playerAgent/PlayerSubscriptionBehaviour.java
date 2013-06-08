package com.utc.cards.player.jade.agent.playerAgent;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.utc.cards.Constants;

public class PlayerSubscriptionBehaviour extends Behaviour
{

    private static final long serialVersionUID = -5440352755054273227L;
    private static Logger log = LoggerFactory
	    .getLogger(PlayerSubscriptionBehaviour.class);

    private PlayerAgent agent;
    private MessageTemplate template;
    private boolean done = false;

    public PlayerSubscriptionBehaviour(PlayerAgent playerAgent)
    {
	this.agent = playerAgent;
    }

    /**
     * send subscribe
     */
    @Override
    public void onStart()
    {
	super.onStart();

	ACLMessage subscription = new ACLMessage(ACLMessage.SUBSCRIBE);
	String convId = "subscription-" + agent.getLocalName();
	subscription.setConversationId(convId);
	subscription.addReceiver(new AID(Constants.CARDS_HOST_AGENT_NAME,
		AID.ISLOCALNAME));
	subscription.setContent(agent.getGmail());
	log.debug("send SUBSCRIPTION");
	agent.send(subscription);

	template = MessageTemplate.MatchConversationId(convId);
    }

    @Override
    public void action()
    {
	/**
	 * wait OK or REFUSE, filter message with convID
	 */
	ACLMessage message = agent.receive(template);
	if (message != null)
	{
	    switch (message.getPerformative()) {
	    case ACLMessage.AGREE:
		// update UI
		log.debug("received AGREE");

		agent.onGameSubscriptionAgree();
		break;
	    case ACLMessage.REFUSE:
		log.debug("received REFUSE");

		// display message "partie pleine"
		agent.onGameSubscriptionRefuse();
		break;
	    }
	    done = true;
	}
	block();
    }

    @Override
    public boolean done()
    {
	return done;
    }

}
