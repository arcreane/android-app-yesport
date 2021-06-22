package com.example.dinoyesport;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BitmapBank {
    private Map <DinoSprites, List<Bitmap>> dinoSpritesList;
    private Bitmap spriteSheet;
    private BitmapFactory.Options options;

    public BitmapBank(MainActivity mainActivity) {
        //get all the user sprites
        options = new BitmapFactory.Options();
        options.inScaled = false;
        spriteSheet = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.dinostand, options);

        //Dino sprites
        dinoSpritesList = new HashMap<>();

        dinoSpritesList.put(DinoSprites.STANDING, new ArrayList<>());
        dinoSpritesList.get(DinoSprites.STANDING).add(getScaledExtractedBitmap(mainActivity, spriteSheet));

        dinoSpritesList.put(DinoSprites.RUNNING, new ArrayList<>());
        dinoSpritesList.get(DinoSprites.RUNNING).add(getScaledExtractedBitmap(mainActivity, BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.dinoleftup, options)));
        dinoSpritesList.get(DinoSprites.RUNNING).add(getScaledExtractedBitmap(mainActivity, BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.dinorightup, options)));

        dinoSpritesList.put(DinoSprites.JUMPING, new ArrayList<>());
        dinoSpritesList.get(DinoSprites.JUMPING).add(getScaledExtractedBitmap(mainActivity, spriteSheet));

        dinoSpritesList.put(DinoSprites.DEAD, new ArrayList<>());
        dinoSpritesList.get(DinoSprites.DEAD).add(getScaledExtractedBitmap(mainActivity, BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.dinobigeyes, options)));

    }

    public Bitmap getFrame(DinoSprites DinoSprites, int p_iFrameNumber){
        return dinoSpritesList.get(DinoSprites).get(p_iFrameNumber);
    }

    private Bitmap getScaledExtractedBitmap(MainActivity mainActivity, Bitmap bitmap){
        float newProportionFactor = mainActivity.getCurrent_screen().getHeight()/300;
        return Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth()*newProportionFactor), (int)(bitmap.getHeight()*newProportionFactor), false);
    }

}
