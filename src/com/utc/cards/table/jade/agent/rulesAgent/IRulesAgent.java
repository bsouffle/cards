package com.utc.cards.table.jade.agent.rulesAgent;

import java.util.List;

import com.utc.cards.model.card.Card;
import com.utc.cards.model.deck.Deck;
import com.utc.cards.model.player.IPlayer;

public interface IRulesAgent
{
    // listen REQUEST initial cards
    public void sendInitialCards();

    public void determinateFirstPlayer();

    // listen REQUEST validate PLAYER_CARDS
    public void validatePlayerCards(List<Card> Cards, IPlayer player);

    public void askAdvice(Deck hand, IPlayer player);

}
