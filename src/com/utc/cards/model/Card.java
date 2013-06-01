package com.utc.cards.model;

public class Card
{
    private String _name;
    private int _resourceId;

    public Card(String name, int resourceId)
    {
	super();
	this._name = name;
	this._resourceId = resourceId;
    }

    public String getName()
    {
	return _name;
    }

    public void setName(String name)
    {
	this._name = name;
    }

    public int getResourceId()
    {
	return _resourceId;
    }

    public void setResourceId(int resourceId)
    {
	this._resourceId = resourceId;
    }

    @Override
    public String toString()
    {
	return "Card [name=" + _name + "]";
    }

    @Override
    public boolean equals(Object o)
    {
	if (o != null && o instanceof Card)
	{
	    return _resourceId == ((Card) o)._resourceId;
	}
	return false;
    }

}
