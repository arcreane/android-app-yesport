package com.example.dinoyesport;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

/**
 * 
 */
public class Dino {

    /**
     * Default constructor
     */
    Bitmap image;
    BitmapBank bitmapBank;
    int y ;
    int isDead;
    boolean running;
    int m_iFrameNb;

    public Dino(BitmapBank bitmapBank) {
        isDead = 0;
        m_iFrameNb= 0;
        this.bitmapBank = bitmapBank;
        running = true;
        image = this.bitmapBank.getFrame(DinoSprites.STANDING, this.m_iFrameNb);
        y = 700;
    }

    /**
     *
     * @param canvas
     */
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, 20, y, null);
    }
    
    public void update() {
        if(running){
            image = this.bitmapBank.getFrame(DinoSprites.RUNNING, (int)Math.floor(m_iFrameNb / 6));
            Log.d("framerate",String.valueOf((int)Math.floor(m_iFrameNb / 6)));
            Log.d("framerate",String.valueOf(m_iFrameNb));
            if(this.m_iFrameNb<8){
                this.m_iFrameNb+=1;
            }
            else {
                this.m_iFrameNb = 0;
            }
        }
        else {
            this.m_iFrameNb = 0;
            image = this.bitmapBank.getFrame(DinoSprites.STANDING, this.m_iFrameNb);
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