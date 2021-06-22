package com.example.dinoyesport;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Sun {

    private final MainActivity mainActivity;
    private Bitmap image;
    float y;
    float x;


    public Sun(BitmapBank bitmapBank, MainActivity mainActivity) {
        image = bitmapBank.getSunSprite();
        this.mainActivity = mainActivity;
        y = 150;
        x = this.mainActivity.getCurrent_screen().getWidth()- 200;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }

    public void update () {

    }


}
