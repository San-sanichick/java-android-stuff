package com.example.mytestapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class DrawView extends SurfaceView implements SurfaceHolder.Callback {
    private DrawThread drawThread;

    private Circle circle;

    public DrawView(Context context) {
        super(context);
        getHolder().addCallback(this);

        circle = new Circle(0, 0, 20, new Paint(Color.argb(0, 0, 0,255)));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.argb(255, 150, 0, 80));
        canvas.drawText("state: ", canvas.getWidth() / 2, canvas.getHeight() / 2, new Paint(Color.WHITE));

        circle.drawSelf(canvas);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        drawThread = new DrawThread(getHolder(), this);
        drawThread.setRunning(true);
        drawThread.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        circle.setPosition(w / 2, h / 2);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        drawThread.setRunning(false);
        boolean retry = true;

        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

