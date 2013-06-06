package com.utc.cards.model.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HumanPlayer extends AbstractPlayer
{

    private static Logger _log = LoggerFactory.getLogger(HumanPlayer.class);

    public HumanPlayer(String name)
    {
	super(name);
    }

    @Override
    public void playTurn()
    {
    }

    public void sendGameAction()
    {
	_log.debug("sendGameAction()");

	// TODO Auto-generated method stub
    }

}
