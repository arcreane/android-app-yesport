package com.example.dinoyesport;

import android.content.SharedPreferences;
import android.graphics.Canvas;

import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    public MainThread thread;
    private boolean gameStarted = false;
    private boolean gameOver = false;
    private float score;
    private float checkpoint;
    private float highScore;
    public Dino dino;
    public Map ground;
    public Sun sun;
    public Obstacle obstacle;
    BitmapBank bitmapBank;
    MainActivity mainActivity;
    SharedPreferences.Editor prefsEditor;
    private MainThread Thread = null;
    private SurfaceHolder holder;
    private boolean isPaused = false;

    public GameView(MainActivity mainActivity) {
        super(mainActivity);
        holder = getHolder();

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
        score = 00000;

        highScore = this.mainActivity.getHighScore();
        SharedPreferences mPrefs = this.mainActivity.getPreferences(this.mainActivity.MODE_PRIVATE);
        prefsEditor = mPrefs.edit();

        bitmapBank = new BitmapBank(this.mainActivity);
        dino = new Dino(bitmapBank, this.mainActivity);
        ground = new Map(bitmapBank, this.mainActivity);
        sun = new Sun(bitmapBank, this.mainActivity);
        obstacle = new Obstacle(bitmapBank, this.mainActivity);

        if(isPaused) {
            onResume();
            if(!gameOver)
                dino.set_Running(true);
        }
        else {
            thread.setRunning(true);
            thread.start();
        }

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
        if(gameStarted)
            if(mainActivity.getGameSpeed() == 15)
            this.score += 1;
            else this.score += 3;
        if(score == 1000) {
            checkpoint = score;
            mainActivity.playCheckpoint();
        }
        if(score - checkpoint == 1000){
            checkpoint = score;
            mainActivity.playCheckpoint();
        }
        this.dino.update();
        this.ground.update();
        this.sun.update();
        this.obstacle.update();
        if(this.obstacle.hasCollided()){
            if(!gameOver)
                mainActivity.playDeath();
            this.dino.set_isDead(true);
            gameOver = true;
            gameStarted = false;
            if(highScore < score){
                highScore = score;
                prefsEditor.putFloat("highScore", highScore);
                prefsEditor.apply();
            }
        }
        return true;
    }


    /**
     * Pauses the physics update & animation.
     */
    public void onPause() {
        synchronized (holder)
        {
            if(isPaused == false)
            {
                isPaused = true;
                gameStarted = false;
                thread.onPause();
            }
        }
    }

    public void onResume()
    {
        synchronized (holder)
        {
            if(isPaused == true)
            {
                if(!thread.isAlive())
                {
                    thread = new MainThread(holder, this, this.mainActivity);
                }
                thread.onResume();
                isPaused = false;
            }
        }
    }
    public Dino getDino() {
        return this.dino;
    }

    public boolean get_GameStarted() {
        return gameStarted;
    }


    public int getScore() {
        return (int) score;
    }

    public int getHighScore() {
        return (int) highScore;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public void reset() {

        score = 00000;
        obstacle.resume();
        gameOver = false;
        this.dino.set_isDead(false);
        this.mainActivity.resetSpeed();
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
