package com.utc.cards.player.jade.agent.playerAgent;

import java.util.Map;

import com.utc.cards.model.deck.Deck;

public interface IPlayerAgent
{
    public void getHostGameName();

    // DO NOT RENAME join() because of JADE agent method name
    public void joinHostGame();

    public void playTurn();

    public void askAdvice();

    public void sendCards(Deck cards);

    public void sendCards(Map<String, Deck> cards);
}
