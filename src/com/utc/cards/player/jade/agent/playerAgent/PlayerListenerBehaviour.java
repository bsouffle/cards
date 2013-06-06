package com.utc.cards.player.jade.agent.playerAgent;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import com.utc.cards.common.jade.Mapper;
import com.utc.cards.model.game.Info;

public class PlayerListenerBehaviour extends CyclicBehaviour
{

    private static final long serialVersionUID = 5312576538932622504L;
    private PlayerAgent agent;
    private MessageTemplate template = MessageTemplate
	    .MatchConversationId("info");

    public PlayerListenerBehaviour(PlayerAgent playerAgent)
    {
	super(playerAgent);
	this.agent = playerAgent;
    }

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
		} catch (Exception ex)
		{

		}
		switch (info.getType()) {
		case INFO:
		    agent.notifyInfo(info.getJson());
		    break;
		case PLAYERS_LIST:
		    String players[] = info.getJson().split("|");
		    agent.notifyPlayersChanged(players);
		    break;
		case PLAYER_TURN:
		    agent.onPlayerTurn();
		    break;
		case GAME_SELECTION:
		    agent.onGameSelection(info.getJson());
		    break;
		case GAME_START:
		    agent.onGameStart();
		    break;
		case GAME_END:

		    break;

		default:
		    break;
		}
	    }
	}
	block();
    }
}
