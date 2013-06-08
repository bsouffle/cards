package com.utc.cards.table.jade.agent.hostAgent;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.utc.cards.common.jade.Mapper;
import com.utc.cards.model.HostModel;
import com.utc.cards.model.game.GameStatus;
import com.utc.cards.model.game.Info;
import com.utc.cards.model.game.InfoType;

/**
 * les demandes sont traitées seulement si les inscriptions sont ouvertes
 * 
 */
public class SubscriptionBehaviour extends CyclicBehaviour
{

    private static final long serialVersionUID = -1033764098211298884L;
    private static Logger log = LoggerFactory
	    .getLogger(SubscriptionBehaviour.class);
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
		// réponse à la demande d'inscription
		reply.setPerformative(ACLMessage.AGREE);
		// content = adresse gmail
		model.getPlayersMap().put(message.getContent(),
			message.getSender());
		log.debug("Subscription AGREE");
		// on informe tous les joueurs de la nouvelle liste des joueurs
		ACLMessage playersMessage = new ACLMessage(ACLMessage.INFORM);
		String players = "";
		playersMessage.setConversationId("info");

		for (Entry<String, AID> entry : model.getPlayersMap()
			.entrySet())
		{
		    playersMessage.addReceiver(entry.getValue());
		    players += entry.getKey() + "|";
		}
		// supprime le dernier "|"
		players = players.substring(0, players.length() - 1);
		Info info = new Info(InfoType.PLAYERS_LIST, players);
		String content = null;
		try
		{
		    content = Mapper.getObjectMapper().writeValueAsString(info);
		} catch (JsonProcessingException e)
		{
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		playersMessage.setContent(content);
		agent.send(playersMessage);// ->playerAgent : listener behaviour

		// mise à jour de la liste des joueurs
		agent.onPlayerJoin(message.getContent());
	    } else
	    {
		// réponse à la demande d'inscription
		reply.setPerformative(ACLMessage.REFUSE);
		log.debug("Subscription REFUSE");
	    }
	    agent.send(reply);// ->playerAgent : listener behaviour
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
