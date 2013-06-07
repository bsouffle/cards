package com.utc.cards.model.game;

import java.util.List;

import com.utc.cards.model.deck.Deck;
import com.utc.cards.model.player.IPlayer;

public abstract class AbstractGameRules implements IRules
{
    @Override
    public abstract IPlayer determineFirstPlayer(final Deck deck,
	    final List<IPlayer> players);

    @Override
    public abstract List<Deck> getInitialCardDistribution(final Deck deck,
	    final List<IPlayer> players);

    public int calculScore(Deck deck)
    {
	// TODO Auto-generated method stub
	return 0;
    }

}
