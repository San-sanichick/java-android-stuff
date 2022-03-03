package com.example.myapplicationsurface;

import androidx.annotation.NonNull;

public class Vector {
    public int x, y;

    public Vector() {
        x = 0;
        y = 0;
    }

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void add(@NonNull Vector v) {
        x += v.x;
        y += v.y;
    }

    public void mult(int num) {
        x *= num;
        y *= num;
    }

    @NonNull
    @Override
    public String toString() {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
