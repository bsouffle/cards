package com.utc.cards.model.deck;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

import com.utc.cards.model.card.Card;

public class Deck extends ArrayList<Card> implements Parcelable
{

    private static final long serialVersionUID = 1944755530666372305L;

    // Création d'un "deck" vide
    public Deck()
    {
	super();
    }

    public Deck(int i)
    {
	super(i);
    }

    public Deck(Deck d)
    {
	super(d);
    }

    public Card getCardByResourceId(int resourceId)
    {
	for (int i = 0; i < size(); i++)
	{
	    if (get(i).getResourceId() == resourceId)
	    {
		return get(i);
	    }
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    public Deck(Parcel in)
    {
	super(in.readArrayList(Card.class.getClassLoader()));
    }

    @Override
    public int describeContents()
    {
	return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
	dest.writeTypedList(this);
    }

    public static final Parcelable.Creator<Deck> CREATOR = new Parcelable.Creator<Deck>() {
	@Override
	public Deck createFromParcel(Parcel in)
	{
	    return new Deck(in);
	}

	@Override
	public Deck[] newArray(int size)
	{
	    return new Deck[size];
	}
    };
}
