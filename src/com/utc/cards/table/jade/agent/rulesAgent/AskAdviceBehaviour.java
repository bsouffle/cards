package com.utc.cards.table.jade.agent.rulesAgent;

import jade.core.behaviours.OneShotBehaviour;

import com.utc.cards.model.HostModel;
import com.utc.cards.model.deck.Deck;
import com.utc.cards.model.game.IRules;
import com.utc.cards.model.player.IPlayer;

public class AskAdviceBehaviour extends OneShotBehaviour
{

    private RulesAgent agent;
    private Deck hand;
    private IPlayer player;
    private HostModel model;
    private IRules rules;

    public AskAdviceBehaviour(RulesAgent a, Deck d, IPlayer p)
    {
        super(a);
        // TODO Auto-generated constructor stub
        this.agent = a;
        this.hand = d;
        this.player = p;
        this.model = agent.getModel();
        this.rules = agent.getModel().getGame().getRules();
    }

    @Override
    public void action()
    {
        // TODO Auto-generated method stub
        Deck deck = this.rules.conseilCoup(this.hand, this.player, this.model);

        // TODO: envoyer la premi�re carte du deck au joueur

    }

}
