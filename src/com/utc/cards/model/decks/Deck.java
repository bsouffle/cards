package com.utc.cards.model.decks;

import java.util.ArrayList;

import com.utc.cards.model.Card;

public class Deck extends ArrayList<Card>
{

    private static final long serialVersionUID = 1944755530666372305L;

    // Cr�ation d'un "deck" vide
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

    // AUTANT UTILISER get(int index)
    // public Card getCard(int index)
    // {
    // try
    // {
    // NE PROVOQUE JAMAIS D'EXCEPTION, renvoie null si pas trouvé
    // return this.get(index);
    // } catch (Exception e)
    // {
    // e.printStackTrace();
    // }
    //
    // return null;
    // }
//
//    public Card getCardByName(String name)
//    {
//	for (int i = 0; i < this.size(); i++)
//	{
//	    if (this.get(i).getName().equals(name))
//	    {
//		return this.get(i);
//	    }
//	}
//	return null;
//    }

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

    // ON ETEND DEJA UNE ARRAYLIST pour éviter d'écrire ce genre de méthode
    public int getNbOfCards()
    {
	return this.size();
    }
}
