package com.example.dinoyesport;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    public MainThread thread;
    public Dino dino;
    public Map ground;

    public GameView(Context context) {
        super(context);

        getHolder().addCallback(this);
        thread = new MainThread(getHolder(),this);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        dino = new Dino(BitmapFactory.decodeResource(getResources(), R.drawable.hell));
        ground = new Map(BitmapFactory.decodeResource(getResources(), R.drawable.ground));
        if (dino.isDead == 0){
            dino = new Dino(BitmapFactory.decodeResource(getResources(), R.drawable.belowrightup));
        }

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update() {

    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        dino.draw(canvas);
        ground.draw(canvas);
    }
}
