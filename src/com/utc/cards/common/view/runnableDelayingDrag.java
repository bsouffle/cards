package com.utc.cards.common.view;

import android.content.ClipData;
import android.view.View;
import android.view.View.DragShadowBuilder;

public class runnableDelayingDrag implements Runnable
{
    private View _view;

    public runnableDelayingDrag(View view)
    {
        _view = view;
    }

    @Override
    public void run()
    {
        if (CardView.NB_POINTERS >= 2)
            return;

        CardView.NB_POINTERS--;

        ClipData data = ClipData.newPlainText("", "");
        DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(_view);
        _view.startDrag(data, shadowBuilder, _view, 0);
        _view.setVisibility(View.INVISIBLE);

    }

}
