package com.utc.cards.model;

public enum Color
{

    CARREAU("\u2666"), COEUR("\u2665"), PIQUE("\u2660"), TREFLE("\u2663");

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
