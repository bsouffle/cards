package com.utc.cards.games.damedepique;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.utc.cards.R;
import com.utc.cards.common.jade.IGameBehaviour;
import com.utc.cards.model.HostModel;
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
    private int manche;
    private int tour;
    private int cardInFold;
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
        int index = 0;
        boolean echanger = true;
        for (IPlayer player : _players)
        {
            if (player.equals(pt.getPlayer()))
                break;
            else
                index++;
        }

        Deck deck = exchange.get(_players.get((index + manche) % 4));
        deck.addAll(pt.getCards());
        exchange.put(_players.get((index + manche) % 4), deck);

        for (Entry<IPlayer, Deck> player : exchange.entrySet())
        {
            if (player.getValue().size() < 3)
                echanger = false;
        }

        if (echanger)
            nextTurn = GameStep.DO_EXCHANGE;
        else
            nextTurn = GameStep.WAIT;
    }

    @Override
    public void playCard(PlayerTry pt)
    {
        // TODO Auto-generated method stub
        indexPlayer = (indexPlayer + 1) % _players.size();
        cardInFold++;

        if (cardInFold == 4)
        {
            nextTurn = GameStep.DETERMINATE_WINNER;
        }
    }

    @Override
    public void distrubutionDone()
    {
        // TODO Auto-generated method stub
        nextTurn = GameStep.ASK_EXCHANGE;
    }

    @Override
    public void determinateScoreDone()
    {
        for (IPlayer player : _players)
        {
            if (player.getScore() > 100)
                nextTurn = GameStep.END_GAME;
        }

        if (nextTurn != GameStep.END_GAME)
        {
            tour = 1;
            cardInFold = 0;
            manche = (manche + 1) % 4;
            nextTurn = GameStep.DISTRIBUTE;
        }
    }

    @Override
    public void determinateWinnerFoldDone(HostModel model)
    {
        tour++;
        cardInFold = 0;

        if (tour <= 13)
        {
            receivers.clear();
            receivers.add(model.getOldFolds().lastElement().getWinner());
        }
        else
            nextTurn = GameStep.DETERMINATE_SCORE;
    }

    @Override
    public void setFirstPlayer(IPlayer player)
    {
        // TODO Auto-generated method stub
        int cmp = 0;
        for (IPlayer currPlayer : _players)
        {
            if (currPlayer.equals(player))
                indexPlayer = cmp;
            cmp++;
        }
        receivers.clear();
        receivers.add(player);
        nextTurn = GameStep.GIVE_PLAYER_TURN;
    }

    @Override
    public List<IPlayer> getExchanger()
    {
        return _players;
    }

    @Override
    public void gameInit()
    {
        // TODO Auto-generated method stub
        tour = 1;
        manche = 1;
        debutTour = true;
        cardInFold = 0;
        partiFini = false;
        nextTurn = GameStep.DISTRIBUTE;
        exchange = new HashMap<IPlayer, Deck>();
    }
}
