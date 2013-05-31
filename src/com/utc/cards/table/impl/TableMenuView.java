package com.utc.cards.table.impl;

import java.util.HashMap;
import java.util.Map;

import com.utc.cards.games.damedepique.DameDePique;
import com.utc.cards.model.game.IGame;
import com.utc.cards.table.controller.TableGameController;
import com.utc.cards.table.view.ITableMenuActivity;

public class TableMenuView implements ITableMenuActivity
{

    private Map<String, IGame> games = new HashMap<String, IGame>();
    private IGame selectedGame = null;

    private TableGameController controller;

    public TableMenuView(TableGameController gamePlayer)
    {
	super();
	this.controller = gamePlayer;
	initGameList();
    }

    private void initGameList()
    {
	IGame game = new DameDePique();
	games.put(game.getName(), game);
    }

    @Override
    public void launchSelectedGame()
    {
	IGame selectedGame = getSelectedGame();
	if (selectedGame != null)
	{
	    controller.loadGame(selectedGame);
	}
    }

    private IGame getSelectedGame()
    {
	return selectedGame;
    }

}
