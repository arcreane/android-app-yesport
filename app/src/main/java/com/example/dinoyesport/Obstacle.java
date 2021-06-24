package com.example.dinoyesport;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;
import java.util.Random;

/**
 * 
 */
public class Obstacle {

    private final MainActivity mainActivity;
    BitmapBank bitmapBank;
    private Bitmap image;
    float y;
    float x;

    private class CactusImage {
        Bitmap image;
        float x;
        float y;

        Rect getObstacle() {
            Rect obstacle = new Rect();
            obstacle.top = (int) y;
            obstacle.bottom = (int) y + image.getHeight();
            obstacle.left = (int) x;
            obstacle.right = (int) x + image.getHeight();

            return obstacle;
        }
    }
    private int randomObstacle;
    private float firstX;
    private int obstacleInterval;
    private int movementSpeed;

    private ArrayList<CactusImage> cactusImageSet;

    /**
     * Default constructor
     */
    public Obstacle(BitmapBank bitmapBank, MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.bitmapBank = bitmapBank;
        cactusImageSet = new ArrayList<CactusImage>();
        firstX = this.mainActivity.getCurrent_screen().getWidth() + (new Random().nextInt((1500 - 800) + 1) + 800);
        addObstacle(firstX);
        for(int i=0; i<3; i++) {
            x = cactusImageSet.get(cactusImageSet.size()-1).x + (new Random().nextInt((3000 - 550) + 1) + 550);;
            addObstacle(x);
        }
    }

    public void draw(Canvas canvas) {
        for(CactusImage img : cactusImageSet) {
            canvas.drawBitmap(img.image,img.x, img.y, null);
        }
        Paint score = new Paint();
        score.setStyle(Paint.Style.FILL);

        score.setColor(Color.WHITE);
        score.setTextSize(60);
        canvas.drawText("HI " +
                this.mainActivity.getCurrent_gameView().getHighScore()
                + " "
                + this.mainActivity.getCurrent_gameView().getScore(), 50 , 50 , score);

        Paint gameOver = new Paint();
        gameOver.setStyle(Paint.Style.FILL);

        gameOver.setColor(Color.WHITE);
        gameOver.setTextSize(90);

        if(this.mainActivity.getCurrent_gameView().isGameOver())
            canvas.drawText("GAME OVER", (float) (this.mainActivity.getCurrent_screen().getWidth()/2.5), (float) (this.mainActivity.getCurrent_screen().getHeight()/2.5), gameOver);
    }

    public void update(){
        if (this.mainActivity.getCurrent_gameView().getDino().get_isDead() == false && this.mainActivity.getCurrent_gameView().get_GameStarted()== true) {
            Iterator<CactusImage> looper = cactusImageSet.iterator();

            CactusImage CactusImage = looper.next();
            CactusImage.x -= this.mainActivity.getGameSpeed();

            while (looper.hasNext()) {
                CactusImage cactusImage = looper.next();
                cactusImage.x -= this.mainActivity.getGameSpeed();
            }

            removeObstacle(CactusImage);
            if (cactusImageSet.size() < 8 && this.mainActivity.getGameSpeed() == Commons.GAME_SPEED) {
                x = cactusImageSet.get(cactusImageSet.size()-1).x + (new Random().nextInt((2000 - 550) + 1) + 550);
                addObstacle(x);
            }

            if (cactusImageSet.size() < 4 && this.mainActivity.getGameSpeed() == Commons.MAX_GAME_SPEED) {
                x = cactusImageSet.get(cactusImageSet.size()-1).x + (new Random().nextInt((3000 - 750) + 1) + 750);
                addObstacle(x);
            }
        }
    }



    /**
     * 
     */
    public boolean hasCollided() {
        for(CactusImage ob : cactusImageSet) {
            if(Dino.getDino().intersect(ob.getObstacle())) {
                return true;
            }
        }
        return false;
    }
    /**
     * 
     */
    public void removeObstacle(CactusImage m_cactusImage) {
        if (m_cactusImage.x < -m_cactusImage.image.getWidth()) {
            cactusImageSet.remove(m_cactusImage);
        }
    }

    /**
     * 
     */
    public void addObstacle(float m_ix) {
        randomObstacle = (int) (Math.random()*5);
        image = this.bitmapBank.getObstacleSprite(randomObstacle);
        if (image.getHeight() == 105)
            y = 730;
        else
            y = 690;
        CactusImage obj = new CactusImage();
        obj.image = image;
        obj.x = m_ix;
        obj.y = y;
        cactusImageSet.add(obj);
    }

    public void resume() {
        cactusImageSet = new ArrayList<CactusImage>();
        addObstacle(firstX);
        for(int i=0; i<3; i++) {
            x = cactusImageSet.get(cactusImageSet.size()-1).x + (new Random().nextInt((3000 - 450) + 1) + 450);;
            addObstacle(x);
        }

    }

}