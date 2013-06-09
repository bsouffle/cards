package com.utc.cards.player.view.listener;

import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.widget.SeekBar;

public class simpleOnScaleGestureListener extends SimpleOnScaleGestureListener
{
    private SeekBar _seekBar;

    public simpleOnScaleGestureListener(SeekBar seekBar)
    {
        _seekBar = seekBar;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector)
    {
        System.out.println(String.valueOf(detector.getScaleFactor()));
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector)
    {
        System.out.println("BEGIN");
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector)
    {
        System.out.println("END");
    }
}
