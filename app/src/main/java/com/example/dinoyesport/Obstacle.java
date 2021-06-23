package com.example.dinoyesport;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;
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
        firstX = this.mainActivity.getCurrent_screen().getWidth() + (new Random().nextInt((800 - 600) + 1) + 600);
        addObstacle(firstX);
        for(int i=0; i<3; i++) {
            x = cactusImageSet.get(cactusImageSet.size()-1).x + (new Random().nextInt((3000 - 450) + 1) + 450);;
            addObstacle(x);
        }
    }

    public void draw(Canvas canvas) {
        for(CactusImage img : cactusImageSet) {
            canvas.drawBitmap(img.image,img.x, img.y, null);
        }
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
            if (cactusImageSet.size() < 4) {
                x = cactusImageSet.get(cactusImageSet.size()-1).x + (new Random().nextInt((3000 - 450) + 1) + 450);
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
                Log.d("collision", "dino is dead ");
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

}