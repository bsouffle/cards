package com.utc.cards.table.jade.agent.rulesAgent;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import com.utc.cards.common.jade.Mapper;
import com.utc.cards.model.game.Info;
import com.utc.cards.model.game.PlayerTry;

public class RulesListenerBehaviour extends CyclicBehaviour
{

    private RulesAgent agent;
    private MessageTemplate template = MessageTemplate.MatchConversationId("info");

    public RulesListenerBehaviour(RulesAgent a)
    {
        super(a);
        this.agent = a;
        // TODO Auto-generated constructor stub
    }

    @Override
    public void action()
    {
        // TODO Auto-generated method stub
        PlayerTry pt;
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
                case INIT_CARDS:
                    agent.sendInitialCards();
                    break;
                case VALID_PLAY:
                    pt = null;
                    try
                    {
                        pt = Mapper.getObjectMapper().readValue(info.getJson(), PlayerTry.class);

                    }
                    catch (Exception e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    agent.validatePlayerCards(pt.getCards(), pt.getPlayer());
                    break;
                case PLAYER_START:
                    agent.determinateFirstPlayer();
                    break;
                case ASK_ADVICE:
                    pt = null;
                    try
                    {
                        pt = Mapper.getObjectMapper().readValue(info.getJson(), PlayerTry.class);

                    }
                    catch (Exception e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    agent.askAdvice(pt.getCards(), pt.getPlayer());
                    break;
                default:
                    break;
                }
            }
        }
        block();
    }
}
