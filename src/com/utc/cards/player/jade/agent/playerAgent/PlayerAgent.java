package com.utc.cards.player.jade.agent.playerAgent;

import jade.core.Agent;

import java.util.Map;

import com.utc.cards.model.deck.Deck;
import com.utc.cards.model.player.AbstractPlayer;

public class PlayerAgent extends Agent implements IPlayerAgent
{

    private static final long serialVersionUID = -7812647282008239070L;

    private AbstractPlayer model;

    @Override
    protected void setup()
    {
	// TODO Auto-generated method stub
	super.setup();
    }

    @Override
    public void getHostGameName()
    {
	// TODO Auto-generated method stub

    }

    @Override
    public void playTurn()
    {
	// TODO Auto-generated method stub

    }

    @Override
    public void askAdvice()
    {
	// TODO Auto-generated method stub

    }

    @Override
    public void sendCards(Deck cards)
    {
	// TODO Auto-generated method stub

    }

    @Override
    public void sendCards(Map<String, Deck> cards)
    {
	// TODO Auto-generated method stub

    }

}
