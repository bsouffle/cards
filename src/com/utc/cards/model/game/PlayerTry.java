package com.utc.cards.model.game;

import java.util.List;

import com.utc.cards.common.view.CardView;
import com.utc.cards.model.player.IPlayer;

public class PlayerTry
{
    private List<CardView> Cards;
    private IPlayer player;

    public List<CardView> getCards()
    {
        return Cards;
    }

    public void setCards(List<CardView> cards)
    {
        Cards = cards;
    }

    public IPlayer getPlayer()
    {
        return player;
    }

    public void setPlayer(IPlayer player)
    {
        this.player = player;
    }
}
