package com.utc.cards.model.card;

import android.os.Parcel;
import android.os.Parcelable;

public class TraditionnalCard extends Card
{

    private Color _color;

    public TraditionnalCard(TraditionnalCardNames traditionnalCardName,
	    Color color, int resourceId)
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

    public enum TraditionnalCardNames implements Parcelable
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

	public static final Parcelable.Creator<TraditionnalCardNames> CREATOR = new Parcelable.Creator<TraditionnalCardNames>() {

	    public TraditionnalCardNames createFromParcel(Parcel in)
	    {
		return TraditionnalCardNames.values()[in.readInt()];
	    }

	    public TraditionnalCardNames[] newArray(int size)
	    {
		return new TraditionnalCardNames[size];
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

    public TraditionnalCard(Parcel in)
    {
	super(in);
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
	super.writeToParcel(dest, flags);
	dest.writeSerializable(_color);
    }

    protected void readFromParcel(Parcel in)
    {
	super.readFromParcel(in);
	_color = (Color) in.readSerializable();
    }

    public static final Parcelable.Creator<TraditionnalCard> CREATOR = new Parcelable.Creator<TraditionnalCard>() {
	@Override
	public TraditionnalCard createFromParcel(Parcel in)
	{
	    return new TraditionnalCard(in);
	}

	@Override
	public TraditionnalCard[] newArray(int size)
	{
	    return new TraditionnalCard[size];
	}
    };
}
