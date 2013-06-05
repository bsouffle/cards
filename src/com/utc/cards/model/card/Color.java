package com.utc.cards.model.card;

import android.os.Parcel;
import android.os.Parcelable;

public enum Color implements Parcelable
{

    DIAMONDS("\u2666"), // (♦)
    HEARTS("\u2665"), // (♥)
    SPADES("\u2660"), // (♠)
    CLUBS("\u2663"); // (♣)

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

    public static final Parcelable.Creator<Color> CREATOR = new Parcelable.Creator<Color>() {

	public Color createFromParcel(Parcel in)
	{
	    return Color.values()[in.readInt()];
	}

	public Color[] newArray(int size)
	{
	    return new Color[size];
	}

    };

    @Override
    public int describeContents()
    {
	return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
	dest.writeInt(ordinal());
    }

}
