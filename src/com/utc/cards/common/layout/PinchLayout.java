package com.utc.cards.common.layout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewParent;
import android.widget.RelativeLayout;

import com.utc.cards.common.layout.listener.PinchListener;

public class PinchLayout extends RelativeLayout
{
    private ScaleGestureDetector _detector;

    public PinchLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        _detector = new ScaleGestureDetector(context, new PinchListener());
    }

    public PinchLayout(Context context)
    {
        super(context);

        _detector = new ScaleGestureDetector(context, new PinchListener());
    }

    @Override
    public ViewParent invalidateChildInParent(int[] location, Rect dirty)
    {
        return super.invalidateChildInParent(location, dirty);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        int count = getChildCount();
        for (int i = 0; i < count; i++)
        {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE)
            {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) child.getLayoutParams();
                child.layout((int) (params.leftMargin), (int) (params.topMargin), (int) (params.leftMargin + child.getMeasuredWidth()), (int) (params.topMargin + child.getMeasuredHeight()));
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return false;
    }

    @Override
    protected void dispatchDraw(Canvas canvas)
    {
        super.dispatchDraw(canvas);

        canvas.restore();
    }
}
