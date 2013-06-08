package com.utc.cards.games.damedepique;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.utc.cards.R;
import com.utc.cards.common.jade.IGameBehaviour;
import com.utc.cards.model.deck.Deck;
import com.utc.cards.model.deck.Traditionnal52Deck;
import com.utc.cards.model.game.AbstractGame;
import com.utc.cards.model.game.IRules;

public class DameDePique extends AbstractGame
{

    private static Logger _log = LoggerFactory.getLogger(DameDePique.class);

    private static final int MIN_PLAYERS = 4;
    private static final int MAX_PLAYERS = 4; // une variante à 6 de la dame de
    // pique existe bien, mais je ne
    // connais pour le moment pas les
    // règles.
    private static final List<Integer> LEGAL_PLAYER_COUNT = new ArrayList<Integer>();
    static
    {
        LEGAL_PLAYER_COUNT.add(MIN_PLAYERS);
        LEGAL_PLAYER_COUNT.add(MAX_PLAYERS);
    }

    public DameDePique()
    {
        super("Dame de Pique", LEGAL_PLAYER_COUNT);
        setLogoResource(R.drawable.logo_dame_de_pique);
    }

    // @Override
    // public void launch()
    // {
    // // TODO Auto-generated method stub
    //
    // }

    @Override
    public List<Integer> getLegalPlayerCounts()
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
    public DameDePiqueTableGameActivity createTableGameActivity()
    {
        return new DameDePiqueTableGameActivity();
    }

    @Override
    public DameDePiquePlayerGameActivity createPlayerGameActivity()
    {
        return new DameDePiquePlayerGameActivity();
    }

    @Override
    public IGameBehaviour createGameBehaviour()
    {
        return new DameDePiqueGameBehaviour();
    }

}
