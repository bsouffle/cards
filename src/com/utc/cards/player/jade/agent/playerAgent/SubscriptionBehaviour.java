package com.utc.cards.player.jade.agent.playerAgent;

import static com.utc.cards.Constants.CARDS_HOST_AGENT_NAME;
import jade.content.Predicate;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.utc.cards.common.ontology.Joined;
import com.utc.cards.common.ontology.Left;

public class SubscriptionBehaviour extends CyclicBehaviour
{
    private static Logger log = LoggerFactory
	    .getLogger(SubscriptionBehaviour.class);

    private PlayerAgent agent;
    private MessageTemplate template;
    private List<AID> participants;
    private static final long serialVersionUID = 2074669021578670687L;

    public SubscriptionBehaviour(PlayerAgent playerAgent)
    {
	super();
	this.agent = playerAgent;
    }

    public void onStart()
    {
	// Subscribe as a chat participant to the ChatManager agent
	ACLMessage subscription = new ACLMessage(ACLMessage.SUBSCRIBE);
	subscription.setLanguage(agent.getCodec().getName());
	subscription.setOntology(agent.getOntolygy().getName());
	String convId = "C-" + agent.getLocalName();
	subscription.setConversationId(convId);
	subscription
		.addReceiver(new AID(CARDS_HOST_AGENT_NAME, AID.ISLOCALNAME));
	agent.send(subscription);
	// Initialize the template used to receive notifications
	// from the HostAgent
	template = MessageTemplate.MatchConversationId(convId);
    }

    public void action()
    {
	// Receives information about people joining and leaving
	// the chat from the ChatManager agent
	ACLMessage msg = agent.receive(template);
	if (msg != null)
	{
	    if (msg.getPerformative() == ACLMessage.INFORM)
	    {
		try
		{
		    Predicate p = (Predicate) agent.getContentManager()
			    .extractContent(msg);
		    if (p instanceof Joined)
		    {
			Joined joined = (Joined) p;
			List<AID> aid = (List<AID>) joined.getWho();
			for (AID a : aid)
			    participants.add(a);
			onPlayersChanged();
		    }
		    if (p instanceof Left)
		    {
			Left left = (Left) p;
			List<AID> aid = (List<AID>) left.getWho();
			for (AID a : aid)
			    participants.remove(a);
			onPlayersChanged();
		    }
		} catch (Exception e)
		{
		    log.error(e.toString());
		    e.printStackTrace();
		}
	    } else
	    {
		handleUnexpected(msg);
	    }
	} else
	{
	    block();
	}
    }

    private void handleUnexpected(ACLMessage msg)
    {
	// TODO Auto-generated method stub

    }

    private void onPlayersChanged()
    {
	log.debug("onPlayersChanged !");
    }
}
