package com.example.dinoyesport;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    public MainThread thread;
    public Dino dino;
    public Map ground;
    int m_iFrameNb;
    BitmapBank bitmapBank;
    MainActivity mainActivity;
    private Display current_screen;

    public GameView(MainActivity mainActivity) {
        super(mainActivity);

        this.mainActivity = mainActivity;
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(),this);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        bitmapBank = new BitmapBank(this.mainActivity);
        dino = new Dino(bitmapBank);
        ground = new Map(BitmapFactory.decodeResource(getResources(), R.drawable.ground));

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

    public boolean update() {
        this.dino.update();
        return true;
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        dino.draw(canvas);
        ground.draw(canvas);
    }
}
