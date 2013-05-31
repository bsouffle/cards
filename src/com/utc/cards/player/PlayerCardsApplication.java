package com.utc.cards.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.utc.cards.model.player.HumanPlayer;
import com.utc.cards.model.player.IPlayer;

public class PlayerCardsApplication
{
    private static Logger _log = LoggerFactory
	    .getLogger(PlayerCardsApplication.class);

    private PlayerActivityController controller;

    private PlayerCardsApplication()
    {
	_log.debug("new PlayerCardsApplication()");

	IPlayer player = new HumanPlayer("Default");
	controller = new PlayerActivityController(player);
	// PlayerMenuViewImpl view = new PlayerMenuViewImpl(controller);
    }

    public static void main(String[] args)
    {
	_log.debug("main()");

	PlayerCardsApplication cardsApp = new PlayerCardsApplication();
	cardsApp.run();
    }

    private void run()
    {
	_log.debug("run()");

	controller.runApp();
    }

}
