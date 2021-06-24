package com.example.dinoyesport;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import androidx.constraintlayout.solver.widgets.Rectangle;

/**
 * 
 */
public class Dino {

    private final MainActivity mainActivity;
    /**
     * Default constructor
     */
    static Bitmap image;
    BitmapBank bitmapBank;
    static float y ;
    static float x;
    private boolean isDead;
    private boolean running;
    private boolean jumping;
    int m_iFrameNb;
    private float m_iYVelocity;
    private float m_fJumpingTime;

    public Dino(BitmapBank bitmapBank, MainActivity mainActivity) {
        isDead = false;
        m_iFrameNb= 0;
        x = 20;
        this.mainActivity = mainActivity;
        this.bitmapBank = bitmapBank;
        running = false;
        image = this.bitmapBank.getFrame(DinoSprites.STANDING, this.m_iFrameNb);
        y = 700;
    }

    public static Rect getDino() {
        Rect dino = new Rect();
        dino.top = (int) y;
        dino.bottom = (int) y + image.getHeight();
        dino.left = (int) x;
        dino.right = (int) x + image.getWidth();

        return dino;
    }


    /**
     *
     * @param canvas
     */
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }
    
    public void update() {

        if (jumping) {
            this.running = false;
            image = this.bitmapBank.getFrame(DinoSprites.JUMPING, 0);
            if (y <= 700) {
                if(y >=0 && this.m_fJumpingTime<Commons.DINO_MAX_JUMP_TIME){
                    this.m_fJumpingTime+=Commons.DINO_JUMP_SPEED;
                    y = y - (m_iYVelocity * (this.mainActivity.getCurrent_screen().getHeight() / 208))*(-1*((this.m_fJumpingTime - Commons.DINO_MAX_JUMP_TIME)/Commons.MAX_FPS));
                }
                else if(this.m_fJumpingTime == Commons.DINO_MAX_JUMP_TIME){
                    this.m_fJumpingTime+=Commons.DINO_JUMP_SPEED;
                }
                else{
                    this.m_fJumpingTime+=Commons.DINO_JUMP_SPEED;
                    y = y + (m_iYVelocity * (this.mainActivity.getCurrent_screen().getHeight() / 208)) * ((this.m_fJumpingTime - Commons.DINO_MAX_JUMP_TIME)/Commons.MAX_FPS);
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
//            Log.d("framerate",String.valueOf((int)Math.floor(m_iFrameNb / 6)));
//            Log.d("framerate",String.valueOf(m_iFrameNb));
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

        if(isDead){
            this.m_iFrameNb = 0;
            this.jumping = false;
            this.running = false;
            image = this.bitmapBank.getFrame(DinoSprites.DEAD, this.m_iFrameNb);

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

    public boolean get_isDead() {
        return isDead;
    }

    public void set_isDead(boolean p_bIsDead) {
         this.isDead = p_bIsDead;
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