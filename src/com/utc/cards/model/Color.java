package com.utc.cards.model;

public enum Color
{

    DIAMONDS("\u2666"), // (♦)
    HEARTS("\u2665"),   // (♥)
    SPADES("\u2660"),   // (♠)
    CLUBS("\u2663");    // (♣)

    private String name;

    private Color(String name)
    {
	this.name = name;
    }

    @Override
    public String toString()
    {
	return name;
    }

}
