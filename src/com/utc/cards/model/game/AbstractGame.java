package com.utc.cards.model.game;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.utc.cards.model.decks.Deck;
import com.utc.cards.model.decks.Traditionnal52Deck;
import com.utc.cards.model.player.IPlayer;

public abstract class AbstractGame implements IGame
{

    private static Logger _log = LoggerFactory.getLogger(AbstractGame.class);

    private String _name;
    protected Deck _deck;
    protected IRules _rules = null;

    private Integer _maxPlayerCount = null;
    private Integer _minPlayerCount = null;
    private int[] _legalPlayerCount = null;

    private List<IPlayer> _players = new ArrayList<IPlayer>();
    // private Map<Player, Deck> hands = new HashMap<Player, Deck>(); // ??
    // plutot via l'agent
    private IPlayer _firstPlayer = null;

    public AbstractGame(String name, int maxPlayerCount, int minPlayerCount)
    {
	this._name = name;
	this._maxPlayerCount = maxPlayerCount;
	this._minPlayerCount = minPlayerCount;
	_log.debug("AbstractGame() (" + minPlayerCount + " to "
		+ maxPlayerCount + " players)");
    }

    public AbstractGame(String name, int[] legalPlayerCount)
    {
	this(name, legalPlayerCount[0],
		legalPlayerCount[legalPlayerCount.length - 1]);
	this._legalPlayerCount = legalPlayerCount;
	logLegalPlayerCountConstructor();
    }

    private void logLegalPlayerCountConstructor()
    {
	boolean first = true;
	StringBuilder legalPlayerString = new StringBuilder();

	for (int index = 0; index < this._legalPlayerCount.length; index++)
	{
	    if (first)
	    {
	    } else if (index == (this._legalPlayerCount.length - 1))
	    {
		legalPlayerString.append(" or ");
	    } else
	    {
		legalPlayerString.append(", ");
	    }

	    legalPlayerString.append(this._legalPlayerCount[index]);
	    first = false;
	}

	_log.debug("AbstractGame() (require " + legalPlayerString + " players)");
    }

    @Override
    public String getName()
    {
	_log.debug("getName()");

	return _name;
    }

    @Override
    public int getMaxPlayerCount()
    {
	_log.debug("getMaxPlayerCount()");

	return _maxPlayerCount;
    }

    @Override
    public int getMinPlayerCount()
    {
	_log.debug("getMinPlayerCount()");

	return _minPlayerCount;
    }

    public final int[] getLegalPlayerCount()
    {
	_log.debug("getLegalPlayerCount()");
	return _legalPlayerCount;
    }

    @Override
    public final List<IPlayer> getPlayers()
    {
	_log.debug("getPlayers()");

	return _players;
    }

    @Override
    public final void addPlayer(IPlayer player)
    {
	_log.debug("addPlayer()");

	_players.add(player);
    }

    @Override
    public final void removePlayer(IPlayer player)
    {
	_log.debug("removePlayer()");
	_players.remove(player);
    }

    // @Override
    // public final Deck getHand(Player player) {
    // log.debug("getHand()");
    // return hands.get(player);
    // }
    //
    // @Override
    // public final void setHand(Player player, Deck hand) {
    // log.debug("setHand()");
    // hands.put(player, hand);
    // }
    //
    // @Override
    // public final void replacePlayer(Player playerToRemove, Player
    // playerToAdd) {
    // log.debug("replacePlayer()");
    // Deck hand = hands.get(playerToRemove);
    // hands.remove(playerToRemove);
    // hands.put(playerToAdd, hand);
    // }

    @Override
    public final IPlayer getFirstPlayer()
    {
	_log.debug("getFirstPlayer()");
	if (_firstPlayer == null)
	{
	    // FIXME : à faire via l'agent
	    _firstPlayer = getRules().determineFirstPlayer(_deck, _players);
	}
	return _firstPlayer;
    }

//    public abstract void launch();
//
//    @Override
//    public void launchGame()
//    {
//	_log.debug("launchGame()");
//
//	launch();
//    }

    protected abstract Deck createDeck();

    @Override
    public final Deck getDeck()
    {
	_log.debug("getAllGameCards()");
	if (_deck == null)
	{
	    _deck = createDeck();
	}
	return _deck;
    }

    // seulement pour l'agent "RulesAgent"
    protected abstract IRules createRules();

    @Override
    public final IRules getRules()
    {
	_log.debug("getAllGameCards()");
	if (_rules == null)
	{
	    _rules = createRules();
	}
	return _rules;
    }

}
