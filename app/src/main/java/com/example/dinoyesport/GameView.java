package com.example.dinoyesport;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    public MainThread thread;
    public Dino dino;
    public Map ground;
    public Sun sun;
    public Obstacle obstacle;
    BitmapBank bitmapBank;
    MainActivity mainActivity;

    public GameView(MainActivity mainActivity) {
        super(mainActivity);

        this.mainActivity = mainActivity;
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(),this, this.mainActivity);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        bitmapBank = new BitmapBank(this.mainActivity);
        dino = new Dino(bitmapBank, this.mainActivity);
        ground = new Map(bitmapBank, this.mainActivity);
        sun = new Sun(bitmapBank, this.mainActivity);
        obstacle = new Obstacle(bitmapBank, this.mainActivity);


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

    public Dino getDino() {
        return this.dino;
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        dino.draw(canvas);
        ground.draw(canvas);
        sun.draw(canvas);
        obstacle.draw(canvas);
    }
}
