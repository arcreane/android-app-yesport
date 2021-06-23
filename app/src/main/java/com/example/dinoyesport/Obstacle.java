package com.example.dinoyesport;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;
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
        int x;
    }
    private int random_obstacle;

    private ArrayList<CactusImage> cactusImageSet;

    /**
     * Default constructor
     */
    public Obstacle(BitmapBank bitmapBank, MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.bitmapBank = bitmapBank;
        random_obstacle = (int) (Math.random()*5);
        image = this.bitmapBank.getObstacleSprite(random_obstacle);
        if (image.getHeight() == 105) y = 730;
        else y = 700;
        x = this.mainActivity.getCurrent_screen().getWidth()- 170;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
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