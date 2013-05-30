package com.utc.cards.model.player;

import com.utc.cards.model.decks.Deck;

public abstract class AbstractPlayer implements IPlayer {
	
	private Deck _hand = new Deck();
	private String _name;
	
	public AbstractPlayer(String name)
	{
		_name = name;
	}
	
	@Override
	public Deck getHand() {
		return _hand;
	}

	@Override
	public void setHand(Deck hand) {
		this._hand = hand;
	}

	public String getName() {
		return _name;
	}
}
