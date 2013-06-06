package com.utc.cards.model.game;

import java.util.List;

import com.utc.cards.model.HostModel;
import com.utc.cards.model.card.Card;
import com.utc.cards.model.deck.Deck;
import com.utc.cards.model.player.IPlayer;

public interface IRules
{

    public List<Deck> getInitialCardDistribution(Deck deck,
	    List<IPlayer> players);

    public IPlayer determineFirstPlayer(final Deck deck, List<IPlayer> players);

    public boolean validerCoup(List<Card> Cards, IPlayer player, HostModel model);

}
