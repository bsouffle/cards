package com.utc.cards.table.jade.agent.rulesAgent;

import jade.core.behaviours.OneShotBehaviour;

import com.utc.cards.model.HostModel;
import com.utc.cards.model.game.IRules;

public class InitScoreBehaviour extends OneShotBehaviour
{

    private RulesAgent agent;
    private HostModel model;
    private IRules rules;

    public InitScoreBehaviour(RulesAgent a)
    {
	super(a);
	this.agent = a;
	this.model = agent.getModel();
	this.rules = agent.getModel().getGame().getRules();
	// TODO Auto-generated constructor stub
    }

    @Override
    public void action()
    {
	// TODO Auto-generated method stub
	int initScore = rules.getInitialScore();
    }

}
