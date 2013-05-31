package com.utc.cards.model;

public class Card
{
    private String _name;
    private int _value;
    private int _resourceId;

    public Card(String name, int value, int resourceId)
    {
	super();
	this._name = name;
	this._value = value;
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

    public int getValue()
    {
	return _value;
    }

    public void setValue(int value)
    {
	this._value = value;
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
	return "Card [name=" + _name + ", value=" + _value + "]";
    }
}
