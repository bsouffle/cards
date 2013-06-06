package com.utc.cards.table.jade.agent.gameAgent;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import com.utc.cards.common.jade.Mapper;
import com.utc.cards.model.game.Info;

public class GameListenerBehaviour extends CyclicBehaviour
{

    private static final long serialVersionUID = -128032335397238397L;
    private GameAgent agent;
    private MessageTemplate template = MessageTemplate
	    .MatchConversationId("info");

    public GameListenerBehaviour(GameAgent gameAgent)
    {
	super(gameAgent);
	this.agent = gameAgent;
    }

    @Override
    public void action()
    {
	// TODO Auto-generated method stub
	ACLMessage msg = agent.receive(template);
	if (msg != null)
	{
	    if (msg.getPerformative() == ACLMessage.INFORM)
	    {
		Info info = null;
		try
		{
		    info = Mapper.getObjectMapper().readValue(msg.getContent(),
			    Info.class);
		} catch (Exception ex)
		{

		}
		switch (info.getType()) {

		}
	    }
	}
	block();
    }

}
