package com.utc.cards.model.game;

import com.utc.cards.model.deck.Deck;
import com.utc.cards.model.player.IPlayer;

public class PlayerTry
{
    private Deck Cards;
    private IPlayer player;

    public Deck getCards()
    {
        return Cards;
    }

    public void setCards(Deck cards)
    {
        Cards = cards;
    }

    public IPlayer getPlayer()
    {
        return player;
    }

    public void setPlayer(IPlayer player)
    {
        this.player = player;
    }
}
