package com.utc.cards.model.game;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.utc.cards.model.decks.Deck;
import com.utc.cards.model.player.IPlayer;

public abstract class AbstractGame implements IGame
{

    private static Logger _log = LoggerFactory.getLogger(AbstractGame.class);

    private Integer _maxPlayerCount = null;
    private Integer _minPlayerCount = null;
    private int[] _legalPlayerCount;
    private Deck _deck;
    private String _name;
    private boolean _initialized = false;
    private List<IPlayer> _players = new ArrayList<IPlayer>();
    // private Map<Player, Deck> hands = new HashMap<Player, Deck>(); // ??
    // plutot via l'agent
    private IPlayer _firstPlayer = null;
    private ArrayList<IRule> _rules = null;

    protected AbstractGame(String name, int maxPlayerCount, int minPlayerCount)
    {
	super();

	_log.debug("AbstractGame() (" + minPlayerCount + " to "
		+ maxPlayerCount + " players)");

	this._name = name;
	this._maxPlayerCount = maxPlayerCount;
	this._minPlayerCount = minPlayerCount;
    }

    public AbstractGame(String name, int maxPlayerCount, int minPlayerCount,
	    int[] legalPlayerCount)
    {

	this(name, maxPlayerCount, minPlayerCount);

	boolean first = true;
	StringBuilder legalPlayerString = new StringBuilder();

	for (int index = 0; index < legalPlayerCount.length; index++)
	{
	    if (first)
	    {
	    } else if (index == (legalPlayerCount.length - 1))
	    {
		legalPlayerString.append(" or ");
	    } else
	    {
		legalPlayerString.append(", ");
	    }

	    legalPlayerString.append(legalPlayerCount[index]);
	    first = false;
	}

	_log.debug("AbstractGame() (require " + legalPlayerString + " players)");
	this._legalPlayerCount = legalPlayerCount;
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

    public final Deck getDeck()
    {
	_log.debug("getAllGameCards()");
	return _deck;
    }

    @Override
    public final void initializeGame()
    {
	_log.debug("initializeGame()");

	// activate game on table
	_deck = loadCards();
	_rules = loadRules();

	_initialized = true;
    }

    public abstract ArrayList<IRule> loadRules();

    public abstract Deck loadCards();

    @Override
    public void init()
    {
	loadRules();
	loadCards();
    }

    @Override
    public final boolean isInitialized()
    {
	_log.debug("isInitialized()");
	return _initialized;
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
	    _firstPlayer = determineFirstPlayer();
	}

	return _firstPlayer;
    }

    @Override
    public abstract IPlayer determineFirstPlayer();

    @Override
    public void launchGame()
    {
	_log.debug("launchGame()");

	launch();
    }

    public abstract void launch();

    public ArrayList<IRule> getRules()
    {
	_log.debug("getRules()");

	return _rules;
    }

    public void setRules(ArrayList<IRule> rules)
    {
	_log.debug("setRules()");

	this._rules = rules;
    }
}
