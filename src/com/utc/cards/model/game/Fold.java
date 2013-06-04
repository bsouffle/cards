package com.utc.cards.model.game;

import java.util.HashMap;
import java.util.Map;

import com.utc.cards.model.deck.Deck;
import com.utc.cards.model.player.IPlayer;

public class Fold
{
    private Map<String, Deck> foldCards = new HashMap<String, Deck>();

    public void setCards(IPlayer player, Deck cards)
    {
	foldCards.put(player.getName(), cards);
    }

    public Deck getCards(IPlayer player)
    {
	return foldCards.get(player);
    }

    public Map<String, Deck> getFoldCards()
    {
	return foldCards;
    }

    public void setFoldCards(Map<String, Deck> cardsMap)
    {
	this.foldCards = cardsMap;
    }
}
