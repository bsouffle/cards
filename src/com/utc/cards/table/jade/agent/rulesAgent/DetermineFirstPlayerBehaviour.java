package com.utc.cards.table.jade.agent.rulesAgent;

import jade.core.behaviours.OneShotBehaviour;

import java.util.List;

import com.utc.cards.model.deck.Deck;
import com.utc.cards.model.game.IRules;
import com.utc.cards.model.player.IPlayer;

public class DetermineFirstPlayerBehaviour extends OneShotBehaviour
{

    private RulesAgent agent;
    private Deck deck;
    private List<IPlayer> players;
    private IRules rules;

    public DetermineFirstPlayerBehaviour(RulesAgent a)
    {
	super(a);
	this.agent = a;
	this.deck = agent.getModel().getGame().getDeck();
	this.players = agent.getModel().getGame().getPlayers();
	this.rules = agent.getModel().getGame().getRules();
	// TODO Auto-generated constructor stub
    }

    @Override
    public void action()
    {
	// TODO Auto-generated method stub
	IPlayer firstPlayer = rules.determineFirstPlayer(deck, players);
    }
}
