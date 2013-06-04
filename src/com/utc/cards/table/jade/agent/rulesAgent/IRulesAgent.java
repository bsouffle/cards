package com.utc.cards.table.jade.agent.rulesAgent;

import com.utc.cards.model.deck.Deck;
import com.utc.cards.model.player.IPlayer;

public interface IRulesAgent
{
    // listen REQUEST initial cards
    public void sendInitialCards();
    
    // listen REQUEST validate PLAYER_CARDS
    public void validatePlayerCards(Deck cards);
    
    // listen REQUEST 
    public void sendAdvice(IPlayer player);
    
    
}
