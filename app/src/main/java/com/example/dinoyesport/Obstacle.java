package com.example.dinoyesport;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Iterator;
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
    }
    private int random_obstacle;

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
        for(int i=0; i<3; i++) {
            random_obstacle = (int) (Math.random()*5);
            image = this.bitmapBank.getObstacleSprite(random_obstacle);
            if (image.getHeight() == 105) y = 730;
            else y = 700;
            x = this.mainActivity.getCurrent_screen().getWidth();
            CactusImage obj = new CactusImage();
            obj.image = image;
            obj.x = x;
            cactusImageSet.add(obj);
        }
    }

    public void draw(Canvas canvas) {
        for(CactusImage img : cactusImageSet) {
            canvas.drawBitmap(img.image,img.x, y, null);
        }
    }

    public void update(){
        if (this.mainActivity.getCurrent_gameView().getDino().get_isDead() == false && this.mainActivity.getCurrent_gameView().get_GameStarted()== true) {
            Iterator<CactusImage> looper = cactusImageSet.iterator();

            CactusImage CactusImage = looper.next();
            CactusImage.x -= this.mainActivity.getGameSpeed();

            while (looper.hasNext()) {
                CactusImage cactusImage = looper.next();
                cactusImage.x -= movementSpeed;
            }

            if (CactusImage.x < -CactusImage.image.getWidth()) {
                cactusImageSet.remove(CactusImage);
                CactusImage.x = cactusImageSet.get(cactusImageSet.size() - 1).x;
                cactusImageSet.add(CactusImage);
            }
        }
    }


    /**
     * 
     */
    public int numberOfObstacle;


    /**
     * 
     */
    public void removeObstacle() {
        // TODO implement here
    }

    /**
     * 
     */
    public void addObstacle() {
        // TODO implement here
    }

}