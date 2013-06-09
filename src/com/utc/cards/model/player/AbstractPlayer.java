package com.utc.cards.model.player;

import java.util.HashMap;
import java.util.Map;

import com.utc.cards.model.deck.Deck;

public abstract class AbstractPlayer implements IPlayer
{
    private String _name;

    private Map<String, Deck> _hands = new HashMap<String, Deck>();

    private int _score;

    public AbstractPlayer(String name)
    {
        _name = name;
    }

    @Override
    public String getName()
    {
        return _name;
    }

    @Override
    public Deck getHand()
    {
        return _hands.get(MAIN_HAND);
    }

    @Override
    public Deck getHand(String handType)
    {
        return _hands.get(handType);
    }

    @Override
    public void setHand(Deck hand)
    {
        this._hands.put(MAIN_HAND, hand);
    }

    @Override
    public void setHand(Deck hand, String handType)
    {
        this._hands.put(handType, hand);
    }

    public int getScore()
    {
        return _score;
    }

    public void setScore(int _score)
    {
        this._score = _score;
    }

}
