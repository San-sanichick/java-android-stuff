package com.example.myapplicationsurface;

import androidx.annotation.NonNull;

public class VectorFloat {
    float x, y;

    public VectorFloat() {
        x = 0F;
        y = 0F;
    }

    public VectorFloat(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void add(VectorFloat v) {
        x += v.x;
        y += v.y;
    }

    public void add(Vector v) {
        x += v.x;
        y += v.y;
    }

    public boolean exceedsAbs(float num) {
        return Math.abs(x) > num || Math.abs(y) > num;
    }

    public void mult(float num) {
        x *= num;
        y *= num;
    }

    public void mult(int num) {
        x *= num;
        y *= num;
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @NonNull
    @Override
    public String toString() {
        return "VectorFloat{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
