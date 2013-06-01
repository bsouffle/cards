package com.utc.cards.table.view;

import java.util.HashMap;
import java.util.Map;

import com.utc.cards.R;
import com.utc.cards.R.layout;
import com.utc.cards.R.menu;
import com.utc.cards.games.damedepique.DameDePique;
import com.utc.cards.model.game.IGame;
import com.utc.cards.table.TableController;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TableSelectGameActivity extends Activity
{
    private Map<String, IGame> games = new HashMap<String, IGame>();
    private IGame selectedGame = null;
    private TableController _controller;

    public TableSelectGameActivity()
    {
	_controller = new TableController();
    }

    public void launchSelectedGame()
    {
	IGame selectedGame = getSelectedGame();
	if (selectedGame != null)
	{
	    _controller.loadGame(selectedGame);
	}
    }

    private IGame getSelectedGame()
    {
	return selectedGame;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_select_game);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.select_game, menu);
	return true;
    }

}
