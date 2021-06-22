package com.example.dinoyesport;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * 
 */
public class Obstacle {

    private final MainActivity mainActivity;
    BitmapBank bitmapBank;
    private Bitmap image;
    float y;
    float x;

    /**
     * Default constructor
     */
    public Obstacle(BitmapBank bitmapBank, MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.bitmapBank = bitmapBank;
        image = this.bitmapBank.getObstacleSprite(2);
        y = 700;
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