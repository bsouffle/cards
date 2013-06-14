package com.utc.cards.common.view;

import android.content.Context;
import android.view.ViewGroup.LayoutParams;

import com.trevorpage.tpsvg.SVGParserRenderer;
import com.trevorpage.tpsvg.SVGView;
import com.utc.cards.model.card.Card;

public class CardView extends SVGView
{

    public static int CARD_WIDTH = 200;

    private int resourceId;
    private Card card;

    public CardView(Card card, Context context)
    {
        super(context);
        this.card = card;
        this.resourceId = card.getResourceId();

        // Tag of the view (-> card name)
        setTag(card.getName());

        // Set the dimension
        LayoutParams p = new LayoutParams(CARD_WIDTH, LayoutParams.WRAP_CONTENT);
        setLayoutParams(p);

        // Parse the SVG resource to view object
        SVGParserRenderer image = new SVGParserRenderer(context, resourceId);
        setSVGRenderer(image, null);

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
