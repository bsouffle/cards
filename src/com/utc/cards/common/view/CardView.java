package com.utc.cards.common.view;

import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.trevorpage.tpsvg.SVGParserRenderer;
import com.trevorpage.tpsvg.SVGView;
import com.utc.cards.common.view.listener.CardTouchListener;
import com.utc.cards.model.card.Card;

public class CardView extends SVGView
{
    public static int CARD_WIDTH = 200;

    private int resourceId;
    private Card card;

    public CardView(Card card, int resourceId, Context _context)
    {
	super(_context);
	this.card = card;
	this.resourceId = resourceId;

	// Tag of the view (-> card name)
	setTag(card.getName());

	// Set the dimension
	LayoutParams p = new LayoutParams(CARD_WIDTH, LayoutParams.WRAP_CONTENT);
	setLayoutParams(p);

	// Parse the SVG resource to view object
	SVGParserRenderer image = new SVGParserRenderer(_context, resourceId);
	setSVGRenderer(image, null);

	// Add listener for handling Drag/Drog actions
	setOnTouchListener(new CardTouchListener());
    }

    public int get_resourceId()
    {
	return resourceId;
    }

    public Card getCard()
    {
	return card;
    }

}
