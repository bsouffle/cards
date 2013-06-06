package com.utc.cards.table.jade.agent.hostAgent;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class InfoBehaviour extends OneShotBehaviour
{

    private static final long serialVersionUID = -6219102384942584128L;
    private String text;
    private AID to;
    private HostAgent agent;

    public InfoBehaviour(HostAgent hostAgent, String text, AID aid)
    {
	this.agent = hostAgent;
	this.text = text;
	this.to = aid;
    }

    @Override
    public void action()
    {
	ACLMessage message = new ACLMessage(ACLMessage.INFORM);
	message.setConversationId("info");
	message.setContent(text);
	message.addReceiver(to);
	agent.send(message);
    }

}
