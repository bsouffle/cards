package com.utc.cards.model.decks;

import java.util.ArrayList;

import com.utc.cards.model.Card;

public class Deck extends ArrayList<Card>
{
	// Création d'un "deck" vide
	public Deck()
	{
		super();
	}
	
	public Deck(int i)
	{
		super(i);
	}
	
	public Deck(Deck d)
	{
		super(d);
	}
	
	public Card getCard(int index)
	{
		try
		{
			return this.get(index);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Card getCardByName(String name)
	{
		for(int i = 0; i < this.size(); i++)
		{
			if(this.get(i).getName().equals(name))
			{
				return this.get(i);
			}
		}
		
		return null;
	}
	
	public int getNbOfCards() 
	{
		return this.size();
	}
}
