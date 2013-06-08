package com.utc.cards.common.view;

import java.util.List;

import android.graphics.Point;
import android.widget.RelativeLayout;

import com.utc.cards.common.view.listener.CardDragListener;

public class CardsContainer
{

    private List<CardView> _cardViews;

    private RelativeLayout _associatedLayout;
    // private Context _context;

    private final int STARTING_X = 20;
    public static int CARD_DISTANCE = 80;

    private Point _screenDimention;

    public CardsContainer(RelativeLayout layout, Point screenDimention,
	    List<CardView> cards)
    {
	_associatedLayout = layout;
	// _context = layout.getContext();
	_cardViews = cards;
	_screenDimention = screenDimention;

	_associatedLayout.setOnDragListener(new CardDragListener(this));

	paint();
    }

    // Use this method only one time
    private void paint()
    {
	int x = STARTING_X;

	for (CardView cardView : _cardViews)
	{
	    // Set the position
	    cardView.setX(x);
	    x += CARD_DISTANCE;

	    _associatedLayout.addView(cardView);
	}
    }

    public void refresh()
    {
	System.out.println("[REFRESH] " + _cardViews.size());

	int x = STARTING_X;

	_associatedLayout.invalidate(); // Refresh the layout

	for (CardView card : _cardViews)
	{
	    // Set the position
	    card.setX(x);
	    card.bringToFront();
	    x += CARD_DISTANCE;
	}
    }

    // Allow to switch the position of one card according to its coordinates (x)
    public void update(String cardName, float x)
    {
	System.out.println("SelectedCard: " + cardName + " - Position: " + x);

	CardView selectedCard = getCardByName(cardName);

	if (selectedCard != null)
	{
	    for (int i = 0; i < _cardViews.size(); i++)
	    {
		int position = STARTING_X + i * CARD_DISTANCE;

		int indexToRemplace = -1;

		if (x >= position && x < position + CardView.CARD_WIDTH)
		{
		    // System.out.println("Position: " + position + " - " +
		    // (position + Card.CARD_WIDTH));
		    indexToRemplace = i + 1;
		} else if (x >= 0 && x < STARTING_X)
		{
		    indexToRemplace = 0;
		}

		if (indexToRemplace >= 0)
		{
		    _cardViews.remove(selectedCard);

		    if (indexToRemplace > _cardViews.size())
		    {
			_cardViews.add(selectedCard);
		    } else
		    {
			_cardViews.add(indexToRemplace, selectedCard);
		    }

		    refresh();

		    return;
		}
	    }
	}
    }

    public void centerCard(String cardName)
    {
	CardView c = getCardByName(cardName);

	if (c != null)
	{

	    int middleIndex = (int) (_cardViews.size() / 2);

	    _cardViews.remove(c);
	    _cardViews.add(middleIndex, c);

	    int x = STARTING_X;

	    boolean hasBeenAdded = false;

	    for (CardView card : _cardViews)
	    {
		if (card.equals(c) || hasBeenAdded)
		{
		    x += _screenDimention.x * 21 / 100;
		    hasBeenAdded = !hasBeenAdded;
		}

		card.setX(x);

		x += CARD_DISTANCE;
	    }
	}
    }

    // Return a card from the card list according to its name
    public CardView getCardByName(String cardName)
    {
	for (CardView c : _cardViews)
	{
	    if (c.getCard().getName().equals(cardName))
		return c;
	}

	return null;
    }

    public void removeCard(CardView c)
    {

	_cardViews.remove(c);

    }

    public List<CardView> getCards()
    {
	return _cardViews;
    }
}
