package com.utc.cards.table.jade.agent.gameAgent;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.Context;

import com.utc.cards.model.HostModel;
import com.utc.cards.model.deck.Deck;
import com.utc.cards.model.player.IPlayer;

// listen for players to send CARD
public class GameAgent extends Agent implements IGameAgent
{
    private static final long serialVersionUID = 1978219257952412130L;
    private static Logger log = LoggerFactory.getLogger(GameAgent.class);

    private HostModel model;
    private Context context;

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
        sd.setType("Game");
        sd.setName("GameAgent");
        dfd.addServices(sd);
        try
        {
            DFService.register(this, dfd);
        }
        catch (FIPAException fe)
        {
            fe.printStackTrace();
        }

        // Add initial behaviours
        addBehaviour(new GameListenerBehaviour(this));

        // expose l'interface pour la rendre accessible par les activity
        registerO2AInterface(IGameAgent.class, this);
    }

    public HostModel getModel()
    {
        return model;
    }

    @Override
    public void giveTurn(IPlayer player)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void startGame()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void sendHand(Deck hand)
    {
        // TODO Auto-generated method stub

    }

}
