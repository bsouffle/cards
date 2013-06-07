package com.utc.cards.table.jade.agent.rulesAgent;

import jade.core.behaviours.OneShotBehaviour;

import java.util.Map;
import java.util.Stack;

import com.utc.cards.model.HostModel;
import com.utc.cards.model.deck.Deck;
import com.utc.cards.model.game.Fold;
import com.utc.cards.model.game.IRules;

public class CalculScoreBehaviour extends OneShotBehaviour
{
    private RulesAgent agent;
    private Stack<Fold> plisFini;
    private HostModel model;
    private IRules rules;

    public CalculScoreBehaviour(RulesAgent a)
    {
	super(a);
	this.agent = a;
	this.model = agent.getModel();
	this.rules = agent.getModel().getGame().getRules();
	this.plisFini = agent.getModel().getOldFolds();
	// TODO Auto-generated constructor stub
    }

    @Override
    public void action()
    {
	// TODO Auto-generated method stub
	Map<String, Deck> score = rules.calculScore(this.plisFini);

    }

}
