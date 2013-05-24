package com.utc.cards.models.game.decks;

import java.util.ArrayList;

import com.utc.cards.models.game.Card;
import com.utc.cards.models.game.GameTypes;

public abstract class Deck 
{
	protected GameTypes _type;
	protected int _nbOfCards = 0;

	protected final ArrayList<Card> _cards = new ArrayList<Card>();
	
	public Card getCard(int index)
	{
		try
		{
			return _cards.get(index);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Card getCardByName(String name)
	{
		for(int i = 0; i < _cards.size(); i++)
		{
			if(_cards.get(i).get_name().equals(name))
			{
				return _cards.get(i);
			}
		}
		
		return null;
	}
	
	public int get_nbOfCards() {
		return _nbOfCards;
	}
}
