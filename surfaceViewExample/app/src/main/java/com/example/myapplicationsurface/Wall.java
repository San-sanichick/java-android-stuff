package com.example.myapplicationsurface;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Wall {
    private int x;
    private int y;
    private int w;
    private int h;

    private Paint paint;

    public Wall(int x, int y, int w, int h, Paint paint) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.paint = paint;
        this.paint.setStyle(Paint.Style.FILL);
    }

    public void drawSelf(Canvas canvas) {
        canvas.drawRect(x, y, x + w, y + h, paint);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }
}
