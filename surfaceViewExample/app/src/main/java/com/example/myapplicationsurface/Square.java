package com.example.myapplicationsurface;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Square {
    private int x, y;
    private int w, h;
    public final VectorFloat speed;
    public final VectorFloat acceleration;
    private final Paint paint;

    public Square(int w, int h) {
        x = 0;
        y = 0;
        this.w = w;
        this.h = h;
        speed = new VectorFloat();
        acceleration = new VectorFloat();
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.argb(255, 0, 200, 0));
    }

    public void update(VectorFloat acceleration) {
//        this.acceleration.add(acceleration);

        speed.add(acceleration);

        x += speed.x;
        y += speed.y;

        speed.mult(0.5F);

//        this.acceleration.mult(0);


//        speed.mult(0.7F);
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

    public boolean isIntersectedByWall(Wall w) {
        if (w.getX() < this.x + this.w && this.x < w.getX() + w.getW() && w.getY() < this.y + this.h) {
            return this.y < w.getY() + w.getH();
        } else {
            return false;
        }
    }

    public void drawSelf(Canvas canvas) {
        canvas.drawRect(x, y, x + w, y + h, paint);
    }
}
