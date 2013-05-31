package com.utc.cards.games.damedepique;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.utc.cards.model.Card;
import com.utc.cards.model.decks.Classic52Deck;
import com.utc.cards.model.decks.Deck;
import com.utc.cards.model.game.AbstractGame;
import com.utc.cards.model.game.IRule;
import com.utc.cards.model.player.IPlayer;

public class DameDePique extends AbstractGame
{

    private static Logger _log = LoggerFactory.getLogger(DameDePique.class);

    private static final int MIN_PLAYERS = 4;
    private static final int MAX_PLAYERS = 6;
    private static final int[] LEGAL_PLAYER_COUNT = { MIN_PLAYERS, MAX_PLAYERS };
    private Deck _deck;

    public DameDePique()
    {
	super("Dame de Pique", MIN_PLAYERS, MAX_PLAYERS, LEGAL_PLAYER_COUNT);
    }

    public ArrayList<IRule> loadRules()
    {
	DameDePiqueRules rules = new DameDePiqueRules();// rules must come from
							// agent
	return rules;
    }

    public Deck loadCards()
    {
	_log.debug("loadCards()");

	_deck = new Classic52Deck();

	return _deck;
    }

    @Override
    public IPlayer determineFirstPlayer()
    {
	// celui qui a la dame de pique commence
	List<IPlayer> players = getPlayers();
	IPlayer firstPlayer = null;
	// FIXME use agent to retrieve all hands
	// for (Player player : players) {
	// if (getHand(player).contains(dameDePique)) {
	// firstPlayer = player;
	// break;
	// }
	// }
	return firstPlayer;
    }

    @Override
    public List<Deck> getInitialCardDistribution()
    {
	int nbPlayers = getPlayers().size();

	List<Deck> initialHands = new ArrayList<Deck>(nbPlayers);

	Deck tas = new Deck(_deck);

	int deckSize = tas.size() / nbPlayers;

	for (int i = 0; i < nbPlayers; i++)
	{
	    Deck initialHand = new Deck(deckSize);
	    // will add 13 or 9 random cards from allCards
	    // remove choosen one at each time

	    for (int j = 0; j < deckSize; j++)
	    {
		Card randomCard = tas.get((int) (Math.random() * (double) tas
			.size()));

		initialHand.add(randomCard);

		tas.remove(randomCard);
	    }

	    initialHands.add(initialHand);
	}

	return initialHands;
    }

    @Override
    public void launch()
    {
	// TODO Auto-generated method stub

    }

    @Override
    public int[] getLegalPlayerCounts()
    {
	return LEGAL_PLAYER_COUNT;
    }

}
