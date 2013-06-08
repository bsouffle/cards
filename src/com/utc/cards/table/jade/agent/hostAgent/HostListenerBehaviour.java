package com.utc.cards.table.jade.agent.hostAgent;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import com.utc.cards.common.jade.Mapper;
import com.utc.cards.model.game.Info;

public class HostListenerBehaviour extends CyclicBehaviour
{
    private static final long serialVersionUID = 6658343850931921678L;
    private HostAgent agent;
    private MessageTemplate template = MessageTemplate
	    .MatchConversationId("info");

    public HostListenerBehaviour(HostAgent hostAgent)
    {
	super(hostAgent);
	this.agent = hostAgent;
    }

    @Override
    public void action()
    {
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
		} catch (Exception e)
		{
		    e.printStackTrace();
		}
		switch (info.getType()) {

		//
		// POUR L'INSTANT RIEN A ECOUTER ICI
		//

		// case INFO:
		// agent.notifyInfo(info.getJson());
		// break;
		// case PLAYERS_LIST:
		// if (info.getJson().contains("|"))
		// {
		// String players[] = info.getJson().split("|");
		// agent.notifyPlayersChanged(players);
		// } else
		// {
		// String players[] = { info.getJson() };
		// agent.notifyPlayersChanged(players);
		// }
		// case PLAYER_TURN:
		// agent.onPlayerTurn();
		// break;
		// case GAME_SELECTION:
		// agent.onGameSelection(info.getJson());
		// break;
		// case GAME_START:
		// agent.onGameStart();
		// break;
		// case GAME_END:
		//
		// break;

		default:
		    break;
		}
	    }
	}
	block();
    }

}
