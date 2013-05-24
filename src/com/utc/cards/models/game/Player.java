package com.utc.cards.models.game;

import java.util.ArrayList;

public class Player 
{
	private String _name;
	private int _score;
	private Hand _hand;
	
	public Player(String name, Game g)
	{
		_name = name;
		_hand = new Hand(g.get_InitialNbOfCardPerPlayer());
	}

	public int get_score() {
		return _score;
	}

	public void set_score(int _score) {
		this._score = _score;
	}

	public String get_name() {
		return _name;
	}
	
	public void giveCard(Card c)
	{
		_hand.addCard(c);
	}
	
	public ArrayList<Card> getHandCards()
	{
		return _hand.get_cards();
	}
}
