package com.utc.cards.player.jade.agent.playerAgent;

import jade.lang.acl.ACLMessage;

import java.util.List;

import com.utc.cards.common.view.CardView;

public interface IPlayerAgent
{
    // DO NOT RENAME join() because of JADE agent method name
    public void joinHostGame();

    public void onPlayerTurn();

    public void askAdvice();

    public ACLMessage sendCards(List<CardView> cards);

    // public void sendCards(Map<String, Deck> cards);
}
