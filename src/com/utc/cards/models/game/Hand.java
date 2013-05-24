package com.utc.cards.models.game;

import java.util.ArrayList;

public class Hand 
{
	
	private ArrayList<Card> _cards = new ArrayList<Card>();

	private final int _nbMaxOfCards;
	
	public Hand(int nbMax)
	{
		_nbMaxOfCards = nbMax;
	}
	
	public void addCard(Card c)
	{
		if(_cards.size() < _nbMaxOfCards)
		{
			_cards.add(c);
		}
	}
	
	public ArrayList<Card> get_cards() 
	{
		return _cards;
	}
	
}
