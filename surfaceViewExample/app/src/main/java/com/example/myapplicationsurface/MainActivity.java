package com.example.myapplicationsurface;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Locale;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class MainActivity extends AppCompatActivity {
    private DrawView drawView;
    private JoystickView joystick;
    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        joystick = findViewById(R.id.joystick);
        resetButton = findViewById(R.id.button);

        drawView = new DrawView(this);
        ((ConstraintLayout) findViewById(R.id.viewWrapper)).addView(drawView);

    }

    @Override
    protected void onPause() {
        super.onPause();
        drawView.getDrawThread().setRunning(false);
    }

    class DrawView extends SurfaceView implements SurfaceHolder.Callback {
        private DrawThread drawThread;
        private final Square square;
        private final Paint textPaint;

        private final ArrayList<Wall> walls = new ArrayList<>();

        private Intent intent;

        public DrawView(Context context) {
            super(context);
            getHolder().addCallback(this);

            square = new Square(100, 100);


            Paint wallPaint = new Paint();
            wallPaint.setStyle(Paint.Style.FILL);
            wallPaint.setColor(Color.RED);
            walls.add(new Wall(50, 50, 20, 200, wallPaint));
            walls.add(new Wall(30, 210, 200, 20, wallPaint));


            textPaint = new Paint();
            textPaint.setStyle(Paint.Style.FILL);
            textPaint.setColor(Color.RED);
            textPaint.setTextSize(50);

            resetButton.setOnClickListener(v -> {
                square.setX(getWidth() / 2);
                square.setY(getHeight() / 2);
            });

            intent = new Intent(context, TestActivity.class);

            joystick.setOnMoveListener((int angle, int strength) -> {
                int c = strength;


                float x = (float) Math.abs(c * Math.cos(angle)) / 20F;
                float y = (float) Math.abs(c * Math.sin(angle)) / 20F;

                if (angle > 90 && angle < 270) {
                    x *= -1;
                }

                if (angle > 0 && angle < 180) {
                    y *= -1;
                }

                VectorFloat acc = new VectorFloat(x, y);

                square.update(acc);
            }, 17);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            square.setX(w / 2);
            square.setY(h / 2);
        }

        public DrawThread getDrawThread() {
            return drawThread;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawColor(Color.argb(255, 0, 0, 50));

            square.drawSelf(canvas);

            walls.forEach(w -> {
                w.drawSelf(canvas);

                if (square.isIntersectedByWall(w)) {
                    try {
                        runOnUiThread(() -> {
                            Toast.makeText(getContext(), "ТЕБЕ ХАНА", Toast.LENGTH_SHORT).show();
                        });
                        Thread.sleep(2000);
                        startActivity(intent);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            canvas.drawText(String.format("AccX: %f", square.acceleration.x), getWidth() - 500, 200, textPaint);
            canvas.drawText(String.format("AccX: %f", square.acceleration.x), getWidth() - 500, 250, textPaint);

            canvas.drawText(String.format("SpeedX: %f", square.speed.x), getWidth() - 500, 300, textPaint);
            canvas.drawText(String.format("SpeedY: %f", square.speed.y), getWidth() - 500, 350, textPaint);
        }

        @Override
        public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceCreated(@NonNull SurfaceHolder holder) {
            drawThread = new DrawThread(getHolder(), this);
            drawThread.setRunning(true);
            drawThread.start();
        }

        @Override
        public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
            boolean retry = true;
            drawThread.setRunning(false);

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


    class DrawThread extends Thread {
        private boolean running = false;
        private final SurfaceHolder surfaceHolder;
        private final DrawView surfaceView;

        public DrawThread(SurfaceHolder surfaceHolder, DrawView surfaceView) {
            this.surfaceHolder = surfaceHolder;
            this.surfaceView = surfaceView;
        }

        public void setRunning(boolean running) {
            this.running = running;
        }

        @SuppressLint("WrongCall")
        @Override
        public void run() {
//            super.run();
            Canvas canvas;
            while (running) {
                canvas = null;
                try {
                    canvas = surfaceHolder.lockCanvas(null);
                    synchronized (surfaceHolder) {
                        surfaceView.onDraw(canvas);
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