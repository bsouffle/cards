package com.utc.cards.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.view.View;

public class RectView extends View
{
    private int _x, _y, _w, _h, _color;
    private boolean _isRelative;

    public RectView(int x, int y, int w, int h, int color, boolean isRelative, Context context)
    {
        super(context);

        _x = x;
        _y = y;
        _w = w;
        _h = h;
        _color = color;
        _isRelative = isRelative;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        // Drawing commands go here
        Path rect = new Path();
        rect.addRect(_x, _y, _x + _w, _y + _h, Direction.CW);
        Paint cPaint = new Paint();
        cPaint.setColor(_color);
        canvas.drawPath(rect, cPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        if (!_isRelative)
        {
            setMeasuredDimension(_w, _h);
        }
        else
        {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}