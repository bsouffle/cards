package com.utc.cards.models.game;

public class NewGame 
{
	private int _nbOfPlayers;
	private Game _associatedGame;
	
	public NewGame(Game game, int nbOfPlayers) throws Exception
	{
		_associatedGame = game;
		
		if(nbOfPlayers < _associatedGame.get_nbMinOfPlayers() || nbOfPlayers > _associatedGame.get_nbMaxOfPlayers())
		{
			throw new Exception("Incoherent number of players");
		}
		
		_nbOfPlayers = nbOfPlayers;
	}
	
	public NewGame(GameTypes type, int nbOfPlayers) throws Exception
	{
		this(new Game(type), nbOfPlayers);
	}

	public int get_nbOfPlayers() {
		return _nbOfPlayers;
	}

	public Game get_associatedGame() {
		return _associatedGame;
	}
}
