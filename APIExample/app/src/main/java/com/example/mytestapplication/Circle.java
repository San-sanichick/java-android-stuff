package com.example.mytestapplication;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Circle {
    private int x;
    private int y;
    private int radius;
    private Paint paint;

    public Circle(int x, int yt, int radius, Paint paint) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.paint = paint;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void drawSelf(Canvas canvas) {
        canvas.drawCircle(x, y, radius, paint);
    }
}
