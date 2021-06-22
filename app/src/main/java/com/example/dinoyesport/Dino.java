package com.example.dinoyesport;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import com.example.dinoyesport.MainActivity;
import android.util.Log;

/**
 * 
 */
public class Dino {

    private final MainActivity mainActivity;
    /**
     * Default constructor
     */
    Bitmap image;
    BitmapBank bitmapBank;
    float y ;
    int isDead;
    boolean running;
    boolean jumping;
    int m_iFrameNb;
    float m_iYVelocity;
    float m_fJumpingTime;

    public Dino(BitmapBank bitmapBank, MainActivity mainActivity) {
        isDead = 0;
        m_iFrameNb= 0;
        this.mainActivity = mainActivity;
        this.bitmapBank = bitmapBank;
        running = false;
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

        if (jumping) {
            this.running = false;
            if (y <= 700) {
                if(y >=0 && this.m_fJumpingTime<Commons.DINO_MAX_JUMP_TIME){
                    this.m_fJumpingTime+=Commons.DINO_JUMP_SPEED;
                    y = y - (m_iYVelocity * (this.mainActivity.getCurrent_screen().getHeight() / 250))*(-1*((this.m_fJumpingTime - Commons.DINO_MAX_JUMP_TIME)/Commons.MAX_FPS));
                }
                else if(this.m_fJumpingTime == Commons.DINO_MAX_JUMP_TIME){
                    this.m_fJumpingTime+=Commons.DINO_JUMP_SPEED;
                }
                else{
                    this.m_fJumpingTime+=Commons.DINO_JUMP_SPEED;
                    y = y + (m_iYVelocity * (this.mainActivity.getCurrent_screen().getHeight() / 250)) * ((this.m_fJumpingTime - Commons.DINO_MAX_JUMP_TIME)/Commons.MAX_FPS);
                }
                image = this.bitmapBank.getFrame(DinoSprites.JUMPING, 0);
            }
            else{
                this.jumping = false;
                this.running = true;
                this.y = 700;
                this.m_fJumpingTime = 0;
            }
        }


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

    public void set_Running(boolean p_bRunning) { running = p_bRunning; }

    /**
     *
     */
    public void set_Jumping(boolean p_bJumping) { jumping = p_bJumping; }

    public float getM_iYVelocity() { return m_iYVelocity; }

    public void setM_iYVelocity(float p_iYVelocity) {
        this.m_iYVelocity = p_iYVelocity;
    }

    public boolean get_Running() { return running; }


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