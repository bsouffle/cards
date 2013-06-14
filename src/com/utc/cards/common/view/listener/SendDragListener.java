package com.utc.cards.common.view.listener;

import android.graphics.Color;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;

import com.utc.cards.common.view.CardView;
import com.utc.cards.common.view.CardsContainer;

public class SendDragListener implements OnDragListener
{
    private CardsContainer _container;

    private View _selectedView;

    public SendDragListener(CardsContainer container)
    {
        _container = container;
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent)
    {
        int action = dragEvent.getAction();

        switch (action)
        {
        case DragEvent.ACTION_DRAG_STARTED:
            // view.setBackgroundColor(Color.YELLOW);
            _selectedView = (View) dragEvent.getLocalState();
            break;

        case DragEvent.ACTION_DRAG_ENTERED:
            view.setBackgroundColor(Color.BLACK);
            break;

        case DragEvent.ACTION_DRAG_EXITED:
            // view.setBackgroundColor(Color.BLUE);
            break;

        case DragEvent.ACTION_DRAG_LOCATION:

            break;

        case DragEvent.ACTION_DROP:
            if (_selectedView != null)
            {
                String cardName = (String) _selectedView.getTag();

                System.out.println("Sending card: " + cardName);

                CardView c = _container.getCardByName(cardName);

                // Sending the card

                _container.removeCard(c);
            }

            break;

        case DragEvent.ACTION_DRAG_ENDED:
            view.setBackgroundColor(Color.GRAY);

        default:
            break;
        }

        return true;
    }

}
