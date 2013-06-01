package com.utc.cards.model;

public class TraditionnalCard extends Card
{

    private Color _color;

    public TraditionnalCard(TraditionnalCardNames traditionnalCardName, Color color, int resourceId)
    {
	super(color.toString() + traditionnalCardName.toString(), resourceId);
	this._color = color;
    }

    public Color getColor()
    {
	return _color;
    }

    public void setColor(Color color)
    {
	this._color = color;
    }

    @Override
    public String toString()
    {
	return _color.toString() + getName();
    }

    public enum TraditionnalCardNames
    {
	_AS("AS"), _2("2"), _3("3"), _4("5"), _5("5"), _6("6"), _7("7"), _8("8"), _9(
		"9"), _10("10"), _J("J"), _Q("Q"), _K("K");

	private String name;

	private TraditionnalCardNames(String name)
	{
	    this.name = name;
	}

	@Override
	public String toString()
	{
	    return name;
	}

    }
}
