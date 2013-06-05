package com.utc.cards.games.damedepique;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.utc.cards.model.deck.Deck;
import com.utc.cards.model.deck.Traditionnal52Deck;
import com.utc.cards.model.game.AbstractGame;
import com.utc.cards.model.game.IRules;
import com.utc.cards.player.view.IPlayerGameActivity;
import com.utc.cards.table.view.ITableGameActivity;

public class DameDePique extends AbstractGame
{

    private static Logger _log = LoggerFactory.getLogger(DameDePique.class);

    private static final int MIN_PLAYERS = 4;
    private static final int MAX_PLAYERS = 4; // une variante à 6 de la dame de pique existe bien, mais je ne connais pour le moment pas les règles.
    private static final int[] LEGAL_PLAYER_COUNT = { MIN_PLAYERS, MAX_PLAYERS };

    public DameDePique()
    {
	super("Dame de Pique", LEGAL_PLAYER_COUNT);
    }

    // @Override
    // public void launch()
    // {
    // // TODO Auto-generated method stub
    //
    // }

    @Override
    public int[] getLegalPlayerCounts()
    {
	return LEGAL_PLAYER_COUNT;
    }

    @Override
    protected IRules createRules()
    {
	_log.debug("createRules()");
	return new DameDePiqueRules();
    }

    @Override
    public Deck createDeck()
    {
	_log.debug("createDeck()");
	return new Traditionnal52Deck();
    }

    @Override
    public ITableGameActivity createTableGameActivity()
    {
	return new DameDePiqueTableGameActivity();
    }

    @Override
    public IPlayerGameActivity createPlayerGameActivity()
    {
	return new DameDePiquePlayerGameActivity();
    }

}
