package com.example.dinoyesport;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 */
public class Map {

    private MainActivity mainActivity;

    /**
     * Default constructor
     */
    private class GroundImage {
        Bitmap image;
        int x;
    }
    private ArrayList<GroundImage> groundImageSet;

    BitmapBank bitmapBank;
    private Bitmap image;
    public static int GROUND_Y;

    public Map(BitmapBank bitmapBank, MainActivity mainActivity) {
        this.bitmapBank = bitmapBank;
        this.mainActivity = mainActivity;
        image = this.bitmapBank.getGroundSprite();
        GROUND_Y = 800;
        groundImageSet = new ArrayList<GroundImage>();

        for(int i=0; i<3; i++) {
            GroundImage obj = new GroundImage();
            obj.image = image;
            obj.x = 0;
            groundImageSet.add(obj);
        }
    }

    public void draw(Canvas canvas) {
        for(GroundImage img : groundImageSet) {
            canvas.drawBitmap(img.image,img.x, GROUND_Y, null);
        }
    }

    public void update () {
        if (this.mainActivity.getCurrent_gameView().getDino().get_isDead() == false && this.mainActivity.getCurrent_gameView().get_GameStarted()== true){
            Iterator<GroundImage> looper = groundImageSet.iterator();
            GroundImage first = looper.next();

            first.x -= this.mainActivity.getGameSpeed();

            int previousX = first.x;
            while(looper.hasNext()) {
                GroundImage next = looper.next();
                next.x = previousX + image.getWidth();
                previousX = next.x;
            }

            if(first.x < -image.getWidth()) {
                groundImageSet.remove(first);
                first.x = previousX + image.getWidth();
                groundImageSet.add(first);
            }
        }

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