package com.utc.cards.table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.utc.cards.games.damedepique.DameDePique;
import com.utc.cards.model.game.IGame;
import com.utc.cards.table.controller.TableGameController;

public class TableCardsApplication
{

    private static Logger _log = LoggerFactory
	    .getLogger(TableCardsApplication.class);

    private TableGameController _controller;

    private TableCardsApplication()
    {
	_log.debug("new TableCardsApplication()");
	_controller = new TableGameController();
	// View menuView = new TableMenuViewImpl(controller);
    }

    public static void main(String[] args)
    {
	_log.debug("main()");

	TableCardsApplication cardsApp = new TableCardsApplication();
	cardsApp.run();
    }

    private void run()
    {
	_log.debug("run()");
	_controller.runApp();

	/* test */
	IGame game = new DameDePique();

	_controller.loadGame(game);
	_controller.launchGame();
	/* end test */

    }

}
