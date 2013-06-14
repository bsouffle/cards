package com.utc.cards.table.jade.agent.gameAgent;

import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.StringWriter;
import java.util.Map.Entry;

import com.utc.cards.common.jade.Mapper;
import com.utc.cards.model.deck.Deck;
import com.utc.cards.model.game.Info;
import com.utc.cards.model.game.InfoType;
import com.utc.cards.model.player.IPlayer;

public class GameListenerBehaviour extends CyclicBehaviour
{

    private static final long serialVersionUID = -128032335397238397L;
    private GameAgent agent;
    private MessageTemplate template = MessageTemplate.MatchConversationId("info");

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
                    info = Mapper.getObjectMapper().readValue(msg.getContent(), Info.class);
                }
                catch (Exception ex)
                {

                }
                switch (info.getType())
                {

                }
            }

            switch (agent.getModel().getGame().getNextTurn())
            {
            case ASK_EXCHANGE:
            {
                ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                StringWriter sw = new StringWriter();

                Info info = new Info();
                info.setType(InfoType.PLAYER_TURN);

                try
                {
                    Mapper.getObjectMapper().writeValue(sw, info);
                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                for (IPlayer player : agent.getModel().getGame().getExchanger())
                {
                    message.addReceiver(agent.getModel().getPlayersMap().get(player));
                }
                message.setContent(sw.toString());

                agent.send(message);
                break;
            }

            case DETERMINATE_FIRST_PLAYER:
            {
                ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                StringWriter sw = new StringWriter();

                Info info = new Info();
                info.setType(InfoType.PLAYER_START);

                try
                {
                    Mapper.getObjectMapper().writeValue(sw, info);
                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                DFAgentDescription template = new DFAgentDescription();
                ServiceDescription sd = new ServiceDescription();
                sd.setType("Rule");
                sd.setName("RuleAgent");
                template.addServices(sd);
                try
                {
                    DFAgentDescription[] result = DFService.search(this.agent, template);
                    if (result.length > 0)
                    {
                        message.addReceiver(result[0].getName());
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                message.setContent(sw.toString());

                agent.send(message);
                break;
            }
            case DETERMINATE_SCORE:
            {
                ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                StringWriter sw = new StringWriter();

                Info info = new Info();
                info.setType(InfoType.CALCUL_SCORE);

                try
                {
                    Mapper.getObjectMapper().writeValue(sw, info);
                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                DFAgentDescription template = new DFAgentDescription();
                ServiceDescription sd = new ServiceDescription();
                sd.setType("Rule");
                sd.setName("RuleAgent");
                template.addServices(sd);
                try
                {
                    DFAgentDescription[] result = DFService.search(this.agent, template);
                    if (result.length > 0)
                    {
                        message.addReceiver(result[0].getName());
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                message.setContent(sw.toString());

                agent.send(message);
                break;
            }
            case DETERMINATE_WINNER:
            {
                ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                StringWriter sw = new StringWriter();

                Info info = new Info();
                info.setType(InfoType.CALCUL_WINNER_FOLD);

                try
                {
                    Mapper.getObjectMapper().writeValue(sw, info);
                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                DFAgentDescription template = new DFAgentDescription();
                ServiceDescription sd = new ServiceDescription();
                sd.setType("Rule");
                sd.setName("RuleAgent");
                template.addServices(sd);
                try
                {
                    DFAgentDescription[] result = DFService.search(this.agent, template);
                    if (result.length > 0)
                    {
                        message.addReceiver(result[0].getName());
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                message.setContent(sw.toString());

                agent.send(message);
                break;
            }
            case DISTRIBUTE:
            {
                ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                StringWriter sw = new StringWriter();

                Info info = new Info();
                info.setType(InfoType.INIT_CARDS);

                try
                {
                    Mapper.getObjectMapper().writeValue(sw, info);
                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                DFAgentDescription template = new DFAgentDescription();
                ServiceDescription sd = new ServiceDescription();
                sd.setType("Rule");
                sd.setName("RuleAgent");
                template.addServices(sd);
                try
                {
                    DFAgentDescription[] result = DFService.search(this.agent, template);
                    if (result.length > 0)
                    {
                        message.addReceiver(result[0].getName());
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                message.setContent(sw.toString());

                agent.send(message);
                break;
            }
            case END_GAME:
                break;
            case DO_EXCHANGE:
            {
                for (Entry<IPlayer, Deck> entry : agent.getModel().getGame().getExchange().entrySet())
                {

                }
                break;
            }
            case GIVE_PLAYER_TURN:
            {
                ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                StringWriter sw = new StringWriter();

                Info info = new Info();
                info.setType(InfoType.PLAYER_TURN);

                try
                {
                    Mapper.getObjectMapper().writeValue(sw, info);
                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                message.setContent(sw.toString());

                for (IPlayer player : agent.getModel().getGame().getTarget())
                {
                    message.addReceiver(agent.getModel().getPlayersMap().get(player));
                }

                agent.send(message);
            }
                break;
            case WAIT:
                break;
            default:
                break;

            }
        }
        block();
    }
}
