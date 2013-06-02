package com.utc.cards.table.jade.agent.gameAgent;

import com.utc.cards.model.deck.Deck;
import com.utc.cards.model.player.IPlayer;

public interface IGameAgent
{

    // called by GameBehaviour
    // can be used to autorise all player to send card at the same time
    // by using Jade Conversation
    public void giveTurn(IPlayer player);

    // UI event
    // sendGameStart()
    public void startGame();

    public void sendHand(Deck hand);

}
