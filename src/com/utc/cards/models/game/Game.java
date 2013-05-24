package com.utc.cards.models.game;

import com.utc.cards.models.game.decks.Classic52Deck;
import com.utc.cards.models.game.decks.Deck;

public class Game 
{
	private String _name;
	private String _description;
	
	private int _nbMinOfPlayers;
	private int _nbMaxOfPlayers;
	
	private int _InitialNbOfCardPerPlayer;
	
	private final Deck _associatedDesk;
	private Rules _rules;

	public Game(GameTypes type)
	{
		switch(type)
		{
			case Hearts:
				_name = "Dame de Pique";
				_description = "blabla...";
				_associatedDesk = new Classic52Deck();
				_nbMinOfPlayers = 4;
				_nbMaxOfPlayers = 4;
				_InitialNbOfCardPerPlayer = 13;
				break;
			default:
				_associatedDesk = null;
		}
	}
	
	public String get_name() {
		return _name;
	}

	public String get_description() {
		return _description;
	}

	public int get_nbMinOfPlayers() {
		return _nbMinOfPlayers;
	}
	
	public int get_nbMaxOfPlayers() {
		return _nbMaxOfPlayers;
	}

	public Deck get_associatedDesk() {
		return _associatedDesk;
	}

	public int get_InitialNbOfCardPerPlayer() {
		return _InitialNbOfCardPerPlayer;
	}
}
