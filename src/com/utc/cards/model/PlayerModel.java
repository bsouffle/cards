package com.utc.cards.model;

import com.utc.cards.model.game.IGame;
import com.utc.cards.model.player.IPlayer;

public class PlayerModel
{
    private IGame game;
    private IPlayer player;

    private static PlayerModel instance;

    public PlayerModel()
    {
	super();
	instance = this;
    }

    // je sais c'est pas beau mais c'est le moyen le plus pratique pour accéder
    // au modèle à partir de TOUTES les activity, sinon il faut compter sur
    // les Intent et Serializable qui est dit lent sur Android, Parcelable est
    // trop compliqué à mettre en place pour tout le modèle
    // du coup paf, une référence statique, pas de confusion possible
    public static PlayerModel Instance()
    {
	return instance;
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
