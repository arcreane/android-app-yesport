package com.example.dinoyesport;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * 
 */
public class Map {

    /**
     * Default constructor
     */
    private Bitmap image;


    public Map(Bitmap bmp) {
        image = bmp;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, 0, 800, null);
    }
    /**
     * 
     */
    public float speed;


    /**
     * 
     */
    public void speedUp() {
        // TODO implement here
    }

    /**
     * 
     */
    public void decelerate() {
        // TODO implement here
    }

}