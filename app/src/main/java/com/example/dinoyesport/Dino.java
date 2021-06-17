package com.example.dinoyesport;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * 
 */
public class Dino {

    /**
     * Default constructor
     */
    private Bitmap image;
    private int y ;
    public int isDead;

    public Dino(Bitmap bmp) {
        isDead = 1;
        image = bmp;
        y = 700;
    }

    /**
     *
     * @param canvas
     */
    public void draw(Canvas canvas) {
        if (isDead == 0){
            canvas.drawBitmap(image, 0, 750, null);
        }
        else {
            canvas.drawBitmap(image, 0, y, null);
        }
    }

    public void jump() {
        y = 500;
    }

    /**
     * 
     */
    public void crouch() {
    }

    /**
     * 
     */
    public void watchIfCrashed() {

    }

}