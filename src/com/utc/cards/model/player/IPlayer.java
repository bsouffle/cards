package com.utc.cards.model.player;

import com.utc.cards.model.decks.Deck;

public interface IPlayer
{
    public void playTurn();

    public Deck getHand();

    public void setHand(Deck hand);

}
