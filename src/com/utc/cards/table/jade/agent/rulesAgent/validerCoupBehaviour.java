package com.utc.cards.table.jade.agent.rulesAgent;

import jade.core.behaviours.OneShotBehaviour;

import java.util.List;

import com.utc.cards.model.HostModel;
import com.utc.cards.model.card.Card;
import com.utc.cards.model.game.IRules;
import com.utc.cards.model.player.IPlayer;

public class validerCoupBehaviour extends OneShotBehaviour
{
    private RulesAgent agent;
    private List<Card> Cards;
    private IPlayer player;
    private HostModel model;
    private IRules rules;

    public validerCoupBehaviour(RulesAgent a, List<Card> c, IPlayer p)
    {
	super(a);
	this.agent = a;
	this.Cards = c;
	this.player = p;
	this.model = agent.getModel();
	this.rules = agent.getModel().getGame().getRules();

	// TODO Auto-generated constructor stub
    }

    @Override
    public void action()
    {
	// TODO Auto-generated method stub
	boolean response = rules.validerCoup(Cards, player, model);
    }

}
