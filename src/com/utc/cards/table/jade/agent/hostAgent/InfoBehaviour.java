package com.utc.cards.table.jade.agent.hostAgent;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Collection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.utc.cards.common.jade.Mapper;
import com.utc.cards.model.game.Info;
import com.utc.cards.model.game.InfoType;

public class InfoBehaviour extends OneShotBehaviour
{

    private static final long serialVersionUID = -6219102384942584128L;
    private String text;
    private Collection<AID> to;
    private HostAgent agent;
    private InfoType type;

    public InfoBehaviour(HostAgent hostAgent, InfoType type, String text,
	    Collection<AID> collection)
    {
	this.type = type;
	this.agent = hostAgent;
	this.text = text;
	this.to = collection;
    }

    @Override
    public void action()
    {
	ACLMessage message = new ACLMessage(ACLMessage.INFORM);
	message.setConversationId("info");
	Info info = new Info(type, text);
	try
	{
	    message.setContent(Mapper.getObjectMapper()
		    .writeValueAsString(info));
	} catch (JsonProcessingException e)
	{
	    e.printStackTrace();
	}
	for (AID aid : to)
	{
	    message.addReceiver(aid);
	}
	agent.send(message);
    }

}
