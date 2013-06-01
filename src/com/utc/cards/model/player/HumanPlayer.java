package com.utc.cards.model.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.utc.cards.player.PlayerController;

public class HumanPlayer extends AbstractPlayer
{

    private static Logger _log = LoggerFactory.getLogger(HumanPlayer.class);

    private PlayerController _playerController;

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
