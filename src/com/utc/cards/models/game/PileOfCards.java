package com.utc.cards.models.game;

import java.util.ArrayList;

import com.utc.cards.models.game.decks.Deck;

public class PileOfCards 
{
	private final ArrayList<Card> _cardsRemaining = new ArrayList<Card>();
	
	
	public PileOfCards(Deck deck)
	{
		// A l'initialisation, on insert toutes les cartes du paquet correspondant
		for(int i = 0; i < deck.get_nbOfCards(); i++)
		{
			_cardsRemaining.add(deck.getCard(i));
		}
	}

	
	public Card getCardOnTheTop()
	{
		Card c = _cardsRemaining.get(_cardsRemaining.size() - 1);
		
		_cardsRemaining.remove(c);
		
		return c;
	}
	
	public Card getRandomCard()
	{
		int random = (int) (Math.random() * _cardsRemaining.size());
		
		Card c = _cardsRemaining.get(random);
		
		_cardsRemaining.remove(c);
		
		return c;
	}
	
	public int get_nbOfCardsRemaining() 
	{
		return _cardsRemaining.size();
	}
}
