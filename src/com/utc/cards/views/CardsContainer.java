package com.utc.cards.views;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Point;
import android.widget.RelativeLayout;

import com.utc.cards.models.game.Card;

public class CardsContainer
{

	private ArrayList<Card> _cards;
	
	private RelativeLayout _associatedLayout;
	private Context _context;
	
	private final int STARTING_X = 20;
	public static int CARD_DISTANCE = 80;
	
	private Point _screenDimention;
	
	public CardsContainer(RelativeLayout layout, Point screenDimention, ArrayList<Card> cards) 
	{
		_associatedLayout = layout;
		_context = layout.getContext();
		_cards = cards;
		_screenDimention = screenDimention;
		
		_associatedLayout.setOnDragListener(new CardDragListener(this));
		
		paint();
	}

	// Use this method only one time
	private void paint()
	{
        int x = STARTING_X;
        
        for(Card card : _cards)
        {       
	        // Set the position
        	card.getView(_context).setX(x);
        	x += CARD_DISTANCE;
	        
	        _associatedLayout.addView(card.getView(_context));
        }
	}
	
	public void refresh()
	{
		System.out.println("[REFRESH] " + _cards.size());
		
		int x = STARTING_X;

		_associatedLayout.invalidate(); // Refresh the layout
		
        for(Card card : _cards)
        {       
	        // Set the position
        	card.getView(_context).setX(x);
        	card.getView(_context).bringToFront();
        	x += CARD_DISTANCE;
        }
	}
	
	// Allow to switch the position of one card according to its coordinates (x)
	public void update(String cardName, float x)
	{
		System.out.println("SelectedCard: " + cardName + " - Position: " + x);
		
		Card selectedCard = getCardByName(cardName);
		
		if(selectedCard != null)
		{
			for(int i = 0; i < _cards.size(); i ++)
			{
				int position = STARTING_X + i * CARD_DISTANCE;
				
				int indexToRemplace = -1;
				
				if(x >= position && x < position + Card.CARD_WIDTH)
				{
					//System.out.println("Position: " + position + " - " + (position + Card.CARD_WIDTH));
					indexToRemplace = i + 1;
				}
				else if(x >= 0 && x < STARTING_X)
				{
					indexToRemplace = 0;
				}
				
				if(indexToRemplace >= 0)
				{
					_cards.remove(selectedCard);
					
					if(indexToRemplace > _cards.size())
					{
						_cards.add(selectedCard);
					}
					else
					{
						_cards.add(indexToRemplace, selectedCard);
					}
					
					refresh();
					
					return;
				}
			}
		}
	}
	
	public void centerCard(String cardName) 
	{
		Card c = getCardByName(cardName);
		
		if(c != null)
		{
			
			int middleIndex = (int) (_cards.size() / 2);
			
			_cards.remove(c);
			_cards.add(middleIndex, c);
			
			int x = STARTING_X;
	        
			boolean hasBeenAdded = false;
			
			for(Card card : _cards)
	        {       
		        if(card.equals(c) || hasBeenAdded)
		        {
		        	x += _screenDimention.x * 21/100;
		        	hasBeenAdded = !hasBeenAdded;
		        }
		       
		        card.getView(_context).setX(x);

	        	x += CARD_DISTANCE;
	        }
		}
	}
	
	// Return a card from the card list according to its name
	public Card getCardByName(String cardName)
	{
		for(Card c : _cards)
		{
			if(c.get_name().equals(cardName)) return c;
		}
		
		return null;
	}
	
	public void removeCard(Card c)
	{
		try
		{
			_cards.remove(c);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	
	public ArrayList<Card> get_cards() {
		return _cards;
	}
}
