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
    private Bitmap groundSprite;
    private Bitmap sunSprite;
    private List<Bitmap> obstacleSprite;

    public BitmapBank(MainActivity mainActivity) {
        //get all the user sprites
        options = new BitmapFactory.Options();
        options.inScaled = false;

        spriteSheet = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.dinostand, options);
        groundSprite = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.ground);
        sunSprite = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.sun);

        // Obstacle sprites
        obstacleSprite = new ArrayList<>();

        obstacleSprite.add(getScaledExtractedBitmap(mainActivity, BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.cactus1, options)));
        obstacleSprite.add(getScaledExtractedBitmap(mainActivity, BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.cactus2, options)));
        obstacleSprite.add(getScaledExtractedBitmap(mainActivity, BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.cactus3, options)));
        obstacleSprite.add(getScaledExtractedBitmap(mainActivity, BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.cactus4, options)));
        obstacleSprite.add(getScaledExtractedBitmap(mainActivity, BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.cactus5, options)));

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

    public Bitmap getSunSprite() {
        return sunSprite;
    }

    public Bitmap getGroundSprite() {
        return groundSprite;
    }

    public Bitmap getObstacleSprite(int p_iObstacleNumber) {
        return obstacleSprite.get(p_iObstacleNumber);
    }

    private Bitmap getScaledExtractedBitmap(MainActivity mainActivity, Bitmap bitmap){
        float newProportionFactor = mainActivity.getCurrent_screen().getHeight()/300;
        return Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth()*newProportionFactor), (int)(bitmap.getHeight()*newProportionFactor), false);
    }

}
