package com.example.dronegcs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class JoystickView extends View {
    private Paint paint;
    private float centerX, centerY, stickX, stickY, radius;

    public JoystickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        centerX = w / 2f;
        centerY = h / 2f;
        stickX = centerX;
        stickY = centerY;
        radius = Math.min(w, h) / 2f * 0.8f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(centerX, centerY, radius, paint);
        canvas.drawCircle(stickX, stickY, radius / 3, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                stickX = Math.max(Math.min(event.getX(), centerX + radius), centerX - radius);
                stickY = Math.max(Math.min(event.getY(), centerY + radius), centerY - radius);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                stickX = centerX;
                stickY = centerY;
                invalidate();
                break;
        }
        return true;
    }

    public float getNormalizedX() { return (stickX - centerX) / radius; }
    public float getNormalizedY() { return (centerY - stickY) / radius; }
}