package com.utc.cards.common.view;

import android.content.Context;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.ViewGroup.LayoutParams;

import com.trevorpage.tpsvg.SVGParserRenderer;
import com.trevorpage.tpsvg.SVGView;
import com.utc.cards.model.card.Card;

public class CardView extends SVGView
{

    public static int CARD_WIDTH = 200;

    public static int NB_POINTERS = 0;

    private int resourceId;
    private Card card;

    private final Handler _handler;
    private runnableDelayingDrag _mLongPressed;

    public CardView(Card card, int resourceId, Context context)
    {
        super(context);
        this.card = card;
        this.resourceId = resourceId;

        // Tag of the view (-> card name)
        setTag(card.getName());

        // Set the dimension
        LayoutParams p = new LayoutParams(CARD_WIDTH, LayoutParams.WRAP_CONTENT);
        setLayoutParams(p);

        // Parse the SVG resource to view object
        SVGParserRenderer image = new SVGParserRenderer(context, resourceId);
        setSVGRenderer(image, null);

        // Add handler for handling Drag/Drog actions
        _handler = new Handler();
        _mLongPressed = new runnableDelayingDrag(this);

    }

    public int get_resourceId()
    {
        return resourceId;
    }

    public Card getCard()
    {
        return card;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        switch (event.getAction() & MotionEvent.ACTION_MASK)
        {
        case MotionEvent.ACTION_DOWN:
        {
            NB_POINTERS++;

            if (NB_POINTERS == 1)
            {
                _handler.postDelayed(_mLongPressed, 1000);
            }
            else if (NB_POINTERS == 2)
            {
                // _pinchLayoutView.setVisibility(View.VISIBLE);
            }

            System.out.println("DOWN : NB_POINTERS = " + NB_POINTERS);

            break;
        }

        case MotionEvent.ACTION_POINTER_DOWN:
        {
            NB_POINTERS++;

            if (NB_POINTERS == 2)
            {

            }
            System.out.println("POINTER_DOWN : NB_POINTERS = " + NB_POINTERS);
            break;
        }

        case MotionEvent.ACTION_UP:
        {
            NB_POINTERS--;
            System.out.println("UP : NB_POINTERS = " + NB_POINTERS);
            break;
        }

        case MotionEvent.ACTION_POINTER_UP:
        {
            NB_POINTERS--;
            System.out.println("POINTER_UP : NB_POINTERS = " + NB_POINTERS);
            break;
        }
        }

        return true;

    }
}
