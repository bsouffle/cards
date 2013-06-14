package com.utc.cards.table.jade.agent.rulesAgent;

import java.util.List;

import com.utc.cards.common.view.CardView;
import com.utc.cards.model.player.IPlayer;

public interface IRulesAgent
{
    // listen REQUEST initial cards
    public void sendInitialCards();

    public void determinateFirstPlayer();

    public void initScore();

    public void calculScore();

    // listen REQUEST validate PLAYER_CARDS
    public void validatePlayerCards(List<CardView> Cards, IPlayer player);

    public void askAdvice(List<CardView> hand, IPlayer player);

    public void determinateWinnerCurrentFold();

}
