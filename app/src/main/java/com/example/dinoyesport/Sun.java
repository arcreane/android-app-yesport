package com.example.dinoyesport;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

public class Sun {

    private final MainActivity mainActivity;
    private Bitmap image;
    float y;
    float x;
    private ArrayList<Float> randomY;

    public Sun(BitmapBank bitmapBank, MainActivity mainActivity) {
        image = bitmapBank.getSunSprite();
        this.mainActivity = mainActivity;
        y = 150;
        x = this.mainActivity.getCurrent_screen().getWidth()- 200;
        randomY = new ArrayList<>();
        randomY.add(-0.26f);
        randomY.add(0.26f);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }

    public void update () {
        if (this.mainActivity.getCurrent_gameView().getDino().get_isDead() == false && this.mainActivity.getCurrent_gameView().get_GameStarted()== true) {
            if (x <= -(image.getWidth()+10)) {
                x = this.mainActivity.getCurrent_screen().getWidth()+50;
                y = 150;
            }
            else {
                x -= 0.5 + (this.mainActivity.getGameSpeed()/60);
                y= y + randomY.get((int) (Math.random()*2));

            }
        }

    }


}
