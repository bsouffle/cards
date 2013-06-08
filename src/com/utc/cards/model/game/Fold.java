package com.utc.cards.model.game;

import java.util.HashMap;
import java.util.Map;

import android.os.Parcel;
import android.os.Parcelable;

import com.utc.cards.model.deck.Deck;
import com.utc.cards.model.player.IPlayer;
import com.utc.cards.utils.Utils;

public class Fold implements Parcelable
{

    // j'ai juste remis une map mais il fuat peut etre changer de conteneur ici
    private Map<String, Deck> foldCards = new HashMap<String, Deck>();

    private IPlayer winner;

    public void setCards(IPlayer player, Deck cards)
    {
        foldCards.put(player.getName(), cards);
    }

    public Deck getCards(IPlayer player)
    {
        return foldCards.get(player);
    }

    public Map<String, Deck> getFoldCards()
    {
        return foldCards;
    }

    public void setFoldCards(Map<String, Deck> cardsMap)
    {
        this.foldCards = cardsMap;
    }

    public Fold(Parcel parcel)
    {
        super();
        foldCards = Utils.readMap(parcel, Deck.class);
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags)
    {
        Utils.writeMap(foldCards, parcel);
    }

    public static final Parcelable.Creator<Fold> CREATOR = new Parcelable.Creator<Fold>()
    {
        @Override
        public Fold createFromParcel(Parcel in)
        {
            return new Fold(in);
        }

        @Override
        public Fold[] newArray(int size)
        {
            return new Fold[size];
        }
    };
}
