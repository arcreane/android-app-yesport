package com.example.dinoyesport;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

public class MainThread extends Thread {
    private final MainActivity mainActivity;
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;
    public static Canvas canvas;
    private final static int MAX_FPS = 60;
    // maximum number of frames to be skipped
    private final static int MAX_FRAME_SKIPS = 1;
    // the frame period
    private final static int FRAME_PERIOD = 1000 / MAX_FPS;
    private Object mPauseLock;
    private boolean mPaused;

    public MainThread(SurfaceHolder surfaceHolder, GameView gameView, MainActivity mainActivity) {

        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
        this.mainActivity = mainActivity;
        mPauseLock = new Object();
        mPaused = false;
    }

    public void onPause() {
        synchronized (mPauseLock) {
            mPaused = true;
        }
    }

    /**
     * resume thread.
     */
    public void onResume() {
        synchronized (mPauseLock) {
            mPaused = false;
            mPauseLock.notifyAll();
        }
    }

    @Override
    public void run() {
        Log.d("Loop", "Starting game loop");

        long beginTime;                      // the time when the cycle begun
        long timeMilliSec; // the time it took for the cycle to execute
        long sleepTime;                      // ms to sleep (<0 if we're behind)
        long targetTime = 1000 / MAX_FPS;


        int framesSkipped = 0;    // number of frames being skipped
        while (running) {
            canvas = null;
            beginTime = System.nanoTime();

            this.mainActivity.getCurrent_gameView().setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch(event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            if(gameView.isGameOver())
                                gameView.reset();
                            if (gameView.get_GameStarted() == false) {
                                gameView.setGameStarted(true);
                            }

                             if (gameView.getDino().getM_iYVelocity()< Commons.DINO_MAX_VERTICAL_VELOCITY) {
                                gameView.getDino().setM_iYVelocity(gameView.getDino().getM_iYVelocity()+Commons.DINO_MAX_VERTICAL_VELOCITY);
                             }
                            mainActivity.playJump();
                            gameView.getDino().set_Jumping(true);
                            break;
                        case MotionEvent.ACTION_UP:
                            // RELEASED
                            break;
                    }
                    return true;
                }
            });

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized(surfaceHolder) {
                    if(mPaused == false)
                    {
                        gameView.update();
                        gameView.draw(canvas);
                    }
                }
            } catch (Exception e) {} finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            timeMilliSec = (System.nanoTime() - beginTime) / 1000000;
            sleepTime = targetTime - timeMilliSec;
            try {
                if (sleepTime > 0) {
                    // if sleepTime > 0 we're OK
                    try {
                        // send the thread to sleep for a short period
                        // very useful for battery saving
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        Log.i("Bug", "exception in sleep : " + e.getMessage());
                    }

                } else {
                    while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
                        // we need to catch up
                        this.gameView.update(); // update without rendering
                        sleepTime += FRAME_PERIOD;    // add frame period to check if in next frame
                        framesSkipped++;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setRunning(boolean isRunning) {
        running = isRunning;
    }
}
