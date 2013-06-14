package com.utc.cards.table.jade.agent.rulesAgent;

import jade.core.behaviours.OneShotBehaviour;

import java.util.ArrayList;
import java.util.List;

import com.utc.cards.common.view.CardView;
import com.utc.cards.model.HostModel;
import com.utc.cards.model.card.Card;
import com.utc.cards.model.game.IRules;
import com.utc.cards.model.player.IPlayer;

public class ValiderCoupBehaviour extends OneShotBehaviour
{
    private RulesAgent agent;
    private List<Card> cards;
    private List<CardView> cardsView;
    private IPlayer player;
    private HostModel model;
    private IRules rules;

    public ValiderCoupBehaviour(RulesAgent a, List<CardView> list, IPlayer p)
    {
        super(a);

        List<Card> c = new ArrayList<Card>();
        for (CardView card : list)
        {
            c.add(card.getCard());
        }

        this.agent = a;
        this.cards = c;
        this.player = p;
        this.model = agent.getModel();
        this.rules = agent.getModel().getGame().getRules();

        // TODO Auto-generated constructor stub
    }

    @Override
    public void action()
    {
        // TODO Auto-generated method stub
        boolean response = rules.validerCoup(cards, player, model);

        // TODO: envoie response au joueur
        if (response)
        {
            // TODO: envoie carte et nom du joueur à l'agent game
        }
    }

}
