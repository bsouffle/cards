package com.utc.cards.table.jade.agent.rulesAgent;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.Context;

import com.utc.cards.model.HostModel;
import com.utc.cards.model.card.Card;
import com.utc.cards.model.deck.Deck;
import com.utc.cards.model.player.IPlayer;

public class RulesAgent extends Agent implements IRulesAgent
{
    private static final long serialVersionUID = -2248519337273621061L;
    private static Logger log = LoggerFactory.getLogger(RulesAgent.class);

    private HostModel model;
    private Context context;

    public HostModel getModel()
    {
        return model;
    }

    @Override
    protected void setup()
    {
        super.setup();
        Object[] args = getArguments();
        if (args != null && args.length > 0)
        {
            if (args[0] instanceof Context)
            {
                context = (Context) args[0];
            }
            else
            {
                log.error("Missing Context arg during agent setup");
            }
            if (args[1] instanceof HostModel)
            {
                model = (HostModel) args[1];
            }
            else
            {
                log.error("Missing HostModel arg during agent setup");
            }
        }

        // Declaration de l'agent sur le réseau
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Rule");
        sd.setName("RuleAgent");
        dfd.addServices(sd);

        try
        {
            DFService.register(this, dfd);
        }
        catch (FIPAException fe)
        {
            fe.printStackTrace();
        }

        //
        // // Add initial behaviours
        addBehaviour(new RulesListenerBehaviour(this));

        // // Initialize the message used to convey spoken sentences
        // spokenMsg = new ACLMessage(ACLMessage.INFORM);
        // spokenMsg.setConversationId(CHAT_ID);

        // expose l'interface pour la rendre accessible par les activity
        registerO2AInterface(IRulesAgent.class, this);
    }

    @Override
    public void sendInitialCards()
    {
        // TODO Auto-generated method stub
        addBehaviour(new InitialCardDistributionBehaviour(this));

    }

    @Override
    public void validatePlayerCards(List<Card> Cards, IPlayer player)
    {
        // TODO Auto-generated method stub
        addBehaviour(new ValiderCoupBehaviour(this, Cards, player));

    }

    @Override
    public void determinateFirstPlayer()
    {
        // TODO Auto-generated method stub
        addBehaviour(new DetermineFirstPlayerBehaviour(this));

    }

    @Override
    public void askAdvice(Deck hand, IPlayer player)
    {
        // TODO Auto-generated method stub
        addBehaviour(new AskAdviceBehaviour(this, hand, player));

    }

    @Override
    public void calculScore()
    {
        // TODO Auto-generated method stub
        addBehaviour(new CalculScoreBehaviour(this));
    }

    @Override
    public void initScore()
    {
        // TODO Auto-generated method stub
        addBehaviour(new InitScoreBehaviour(this));
    }

    @Override
    public void determinateWinnerCurrentFold()
    {
        // TODO Auto-generated method stub

    }
}
