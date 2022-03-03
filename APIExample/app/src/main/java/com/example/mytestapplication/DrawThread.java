package com.example.mytestapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class DrawThread extends Thread {
    private final SurfaceHolder surfaceHolder;
    private final DrawView drawView;
    private volatile boolean running = true;

    public DrawThread(SurfaceHolder surfaceHolder, DrawView drawView) {
        this.surfaceHolder = surfaceHolder;
        this.drawView = drawView;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @SuppressLint("WrongCall")
    @Override
    public void run() {
        Canvas canvas;
        while (running) {
            canvas = null;

            if (canvas != null) {
                try {
                    canvas = surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {
                        drawView.onDraw(canvas);
                    }
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }
}
