package com.utc.cards.model.player;

import com.utc.cards.model.deck.Deck;

public interface IPlayer
{
    public void playTurn();

    public String getName();

    public Deck getHand();
    
    public Deck getHand(String handType);

    public void setHand(Deck hand);

    public void setHand(Deck hand, String handType);

}
