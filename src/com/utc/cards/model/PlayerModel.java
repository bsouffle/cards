package com.utc.cards.model;

import com.utc.cards.model.game.IGame;
import com.utc.cards.model.player.IPlayer;

public class PlayerModel
{
    private IGame game;
    private IPlayer player;

    public PlayerModel()
    {
	super();
    }

    public IGame getGame()
    {
	return game;
    }

    public void setGame(IGame game)
    {
	this.game = game;
    }

    public IPlayer getPlayer()
    {
	return player;
    }

    public void setPlayer(IPlayer player)
    {
	this.player = player;
    }
}
