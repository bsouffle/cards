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
import com.utc.cards.model.game.GameStep;
import com.utc.cards.model.game.IRules;
import com.utc.cards.model.game.PlayerTry;
import com.utc.cards.model.player.IPlayer;
import com.utc.cards.table.jade.agent.gameAgent.GameAgent;

public class DameDePique extends AbstractGame
{

    private static Logger _log = LoggerFactory.getLogger(DameDePique.class);

    private static final int MIN_PLAYERS = 4;
    private static final int MAX_PLAYERS = 4;
    // une variante à 6 de la dame de
    // pique existe bien, mais je ne
    // connais pour le moment pas les
    // règles.
    private static final List<Integer> LEGAL_PLAYER_COUNT = new ArrayList<Integer>();
    private boolean debutTour;
    private int tour;
    private int nbPli;
    private boolean partiFini;

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
    public IGameBehaviour createGameBehaviour(GameAgent agent)
    {
        return new DameDePiqueGameBehaviour();
    }

    @Override
    public void receiveCard(PlayerTry pt)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void playCard(PlayerTry pt)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void distrubutionDone()
    {
        // TODO Auto-generated method stub
        nextTurn = GameStep.DETERMINATE_FIRST_PLAYER;
    }

    @Override
    public void setFirstPlayer(IPlayer player)
    {
        // TODO Auto-generated method stub
        receivers.clear();
        receivers.add(player);
        nextTurn = GameStep.GIVE_PLAYER_TURN;
    }

    @Override
    public void gameInit()
    {
        // TODO Auto-generated method stub
        tour = 1;
        debutTour = true;
        nbPli = 0;
        partiFini = false;
        nextTurn = GameStep.DISTRIBUTE;
    }
}
