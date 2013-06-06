package com.utc.cards.model.game;

import java.util.List;

import com.utc.cards.model.card.Card;
import com.utc.cards.model.player.IPlayer;

public class PlayerTry
{
    private List<Card> Cards;
    private IPlayer player;

    public List<Card> getCards()
    {
	return Cards;
    }

    public void setCards(List<Card> cards)
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
