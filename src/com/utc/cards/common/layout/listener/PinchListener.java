package com.utc.cards.common.layout.listener;

import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;

public class PinchListener extends SimpleOnScaleGestureListener
{
    public PinchListener()
    {

    }

    @Override
    public boolean onScale(ScaleGestureDetector detector)
    {
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector)
    {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector)
    {

    }
}
