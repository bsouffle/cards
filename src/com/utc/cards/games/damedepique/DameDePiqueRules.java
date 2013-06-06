package com.utc.cards.games.damedepique;

import java.util.ArrayList;
import java.util.List;

import com.utc.cards.R;
import com.utc.cards.model.HostModel;
import com.utc.cards.model.card.Card;
import com.utc.cards.model.deck.Deck;
import com.utc.cards.model.game.AbstractGameRules;
import com.utc.cards.model.player.IPlayer;

public class DameDePiqueRules extends AbstractGameRules
{

    @Override
    public IPlayer determineFirstPlayer(final Deck deck,
	    final List<IPlayer> players)
    {
	Card deuxDeTrefle = deck.getCardByResourceId(R.raw.cards_2c);
	// celui qui a le deux de tr�fle commence
	IPlayer firstPlayer = null;
	for (IPlayer player : players)
	{
	    if (player.getHand().contains(deuxDeTrefle))
	    {
		firstPlayer = player;
		break;
	    }
	}
	return firstPlayer;
    }

    @Override
    public List<Deck> getInitialCardDistribution(final Deck deck,
	    final List<IPlayer> players)
    {
	List<Deck> initialHands = new ArrayList<Deck>(players.size());
	int nbPlayers = players.size();
	Deck tas = new Deck(deck);
	int playerDeckSize = tas.size() / nbPlayers;

	for (int i = 0; i < nbPlayers; i++)
	{
	    Deck initialHand = new Deck(playerDeckSize);
	    // will add 13 or 9 random cards from allCards
	    // remove choosen one at each time
	    for (int j = 0; j < playerDeckSize; j++)
	    {
		Card randomCard = getRandomCard(tas);
		initialHand.add(randomCard);
		tas.remove(randomCard);
	    }
	    initialHands.add(initialHand);
	}
	return initialHands;
    }

    private Card getRandomCard(Deck deck)
    {
	if (deck != null && !deck.isEmpty())
	{
	    return deck.get((int) (Math.random() * (double) deck.size()));
	}
	return null;
    }

    @Override
    public boolean validerCoup(List<Card> Cards, IPlayer player, HostModel model)
    {
	// TODO Auto-generated method stub
	return false;
    }

}
