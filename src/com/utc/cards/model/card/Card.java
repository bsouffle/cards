package com.utc.cards.model.card;

import android.os.Parcel;
import android.os.Parcelable;

public class Card implements Parcelable
{
    public static final int VERTICAL = 0;
    public static final int HORIZONTAL = 1;

    private String name;
    private int resourceId;
    private int orientation;
    private boolean visible;

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
	this.name = name;
	this.resourceId = resourceId;
	this.visible = visible;
	this.orientation = orientation;
    }

    public String getName()
    {
	return name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    public int getResourceId()
    {
	return resourceId;
    }

    public void setResourceId(int resourceId)
    {
	this.resourceId = resourceId;
    }

    public int getOrientation()
    {
	return orientation;
    }

    public void setOrientation(int orientation)
    {
	this.orientation = orientation;
    }

    public boolean isVisible()
    {
	return visible;
    }

    public void setVisible(boolean visible)
    {
	this.visible = visible;
    }

    @Override
    public String toString()
    {
	return "Card [name=" + name + "]";
    }

    @Override
    public boolean equals(Object o)
    {
	if (o != null && o instanceof Card)
	{
	    return resourceId == ((Card) o).resourceId;
	}
	return false;
    }

    public Card(Parcel in)
    {
	readFromParcel(in);
    }

    @Override
    public int describeContents()
    {
	return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
	dest.writeString(name);
	dest.writeInt(resourceId);
	dest.writeInt(orientation);
	dest.writeInt(visible ? 1 : 0);
    }

    protected void readFromParcel(Parcel in)
    {
	name = in.readString();
	resourceId = in.readInt();
	orientation = in.readInt();
	visible = in.readInt() == 1;
    }

    public static final Parcelable.Creator<Card> CREATOR = new Parcelable.Creator<Card>() {
	@Override
	public Card createFromParcel(Parcel in)
	{
	    return new Card(in);
	}

	@Override
	public Card[] newArray(int size)
	{
	    return new Card[size];
	}
    };
}
