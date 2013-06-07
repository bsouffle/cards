package com.utc.cards.model.game;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.utc.cards.model.deck.Deck;
import com.utc.cards.model.player.IPlayer;

public abstract class AbstractGame implements IGame
{

    private static Logger log = LoggerFactory.getLogger(AbstractGame.class);

    protected String name;
    protected Deck deck;
    protected IRules rules = null;
    protected GameStatus status = GameStatus.SUBSCRIPTION;
    protected int resource;

    protected Integer maxPlayerCount = null;
    protected Integer minPlayerCount = null;
    protected int[] legalPlayerCount = null;
    /**
     * la liste des joueurs dans leur ordre de jeu (après lancement de la
     * partie, sinon ordre non garanti)
     */
    protected List<IPlayer> _players = new ArrayList<IPlayer>();

    public AbstractGame(String name, int maxPlayerCount, int minPlayerCount)
    {
	this.name = name;
	this.maxPlayerCount = maxPlayerCount;
	this.minPlayerCount = minPlayerCount;
	log.debug("AbstractGame() (" + minPlayerCount + " to " + maxPlayerCount
		+ " players)");
    }

    public AbstractGame(String name, int[] legalPlayerCount)
    {
	this(name, legalPlayerCount[0],
		legalPlayerCount[legalPlayerCount.length - 1]);
	this.legalPlayerCount = legalPlayerCount;
	logLegalPlayerCountConstructor();
    }

    private void logLegalPlayerCountConstructor()
    {
	boolean first = true;
	StringBuilder legalPlayerString = new StringBuilder();

	for (int index = 0; index < this.legalPlayerCount.length; index++)
	{
	    if (first)
	    {
	    } else if (index == (this.legalPlayerCount.length - 1))
	    {
		legalPlayerString.append(" or ");
	    } else
	    {
		legalPlayerString.append(", ");
	    }

	    legalPlayerString.append(this.legalPlayerCount[index]);
	    first = false;
	}

	log.debug("AbstractGame() (require " + legalPlayerString + " players)");
    }

    @Override
    public String getName()
    {
	log.debug("getName()");

	return name;
    }

    @Override
    public int getMaxPlayerCount()
    {
	log.debug("getMaxPlayerCount()");

	return maxPlayerCount;
    }

    @Override
    public int getMinPlayerCount()
    {
	log.debug("getMinPlayerCount()");

	return minPlayerCount;
    }

    public final int[] getLegalPlayerCount()
    {
	log.debug("getLegalPlayerCount()");
	return legalPlayerCount;
    }

    @Override
    public final List<IPlayer> getPlayers()
    {
	log.debug("getPlayers()");
	return _players;
    }

    @Override
    public final void addPlayer(IPlayer player)
    {
	log.debug("addPlayer()");

	_players.add(player);
    }

    @Override
    public final void removePlayer(IPlayer player)
    {
	log.debug("removePlayer()");
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

    protected abstract Deck createDeck();

    @Override
    public final Deck getDeck()
    {
	log.debug("getAllGameCards()");
	if (deck == null)
	{
	    deck = createDeck();
	}
	return deck;
    }

    // seulement pour l'agent "RulesAgent"
    protected abstract IRules createRules();

    @Override
    public final IRules getRules()
    {
	log.debug("getAllGameCards()");
	if (rules == null)
	{
	    rules = createRules();
	}
	return rules;
    }

    @Override
    public GameStatus getStatus()
    {
	return status;
    }

    @Override
    public void setStatus(GameStatus status)
    {
	this.status = status;
    }

    @Override
    public int getLogoResource()
    {
	return resource;
    }

    @Override
    public void setLogoResource(int res)
    {
	resource = res;
    }
}
