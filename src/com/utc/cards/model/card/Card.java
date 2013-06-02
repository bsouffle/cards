package com.utc.cards.model.card;

public class Card
{
    public static final int VERTICAL = 0;
    public static final int HORIZONTAL = 1;

    private String _name;
    private int _resourceId;
    private int _orientation;
    private boolean _visible;

    public Card(String name, int resourceId)
    {
	this(name, resourceId, true, VERTICAL);
    }

    public Card(String name, int resourceId, boolean visible)
    {
	this(name, resourceId, visible, VERTICAL);

    }

    public Card(String name, int resourceId, boolean visible, int orientation)
    {
	this._name = name;
	this._resourceId = resourceId;
	this._visible = visible;
	this._orientation = orientation;
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

    public int getOrientation()
    {
	return _orientation;
    }

    public void setOrientation(int orientation)
    {
	this._orientation = orientation;
    }

    public boolean isVisible()
    {
	return _visible;
    }

    public void setVisible(boolean visible)
    {
	this._visible = visible;
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
