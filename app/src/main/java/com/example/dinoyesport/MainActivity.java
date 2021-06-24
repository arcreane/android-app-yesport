package com.example.dinoyesport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.content.SharedPreferences;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    private Display current_screen;
    private GameView current_gameView;
    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;
    private SensorEventListener gyroscopeEventListener;
    private int gameSpeed = Commons.GAME_SPEED;
    private float highScore;
    MediaPlayer jumpSound;
    MediaPlayer deathSound;
    MediaPlayer scoreCheckpoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        SharedPreferences highScoreSaved = getPreferences(MODE_PRIVATE);
        float saved = highScoreSaved.getFloat("highScore", 00000);
        hideNavigationBar();
        highScore = (saved == 0 ? 0 : saved);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        jumpSound = MediaPlayer.create(this, R.raw.dinojump);
        deathSound = MediaPlayer.create(this, R.raw.dinodeath);
        scoreCheckpoint = MediaPlayer.create(this, R.raw.scorecheckpoint);

        if (gyroscopeSensor == null) Toast.makeText(this, "this device have no gyroscope", Toast.LENGTH_LONG).show();

        gyroscopeEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.values[2] > 1.5f)
                gameSpeed = Commons.GAME_SPEED;
                if (event.values[2] < -1.5f)
                gameSpeed = Commons.MAX_GAME_SPEED;
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        current_gameView = new GameView(this);

        // hide the Top title bar
        getSupportActionBar().hide();

        // resquest landscape orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(current_gameView);
        current_screen = getWindowManager().getDefaultDisplay();
        Toast.makeText(this, "Tap to start !", Toast.LENGTH_SHORT).show();

    }
    public Display getCurrent_screen() { return this.current_screen; }
    public GameView getCurrent_gameView() { return current_gameView; }
    public int getGameSpeed() { return gameSpeed; }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(gyroscopeEventListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_GAME);
        current_gameView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(gyroscopeEventListener);
        current_gameView.onPause();

    }

    private void hideNavigationBar() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    }

    public void restart() {
        recreate();
    }

    public void playJump() {
        jumpSound.start();
    }

    public float getHighScore() {
        return highScore;
    }

    public void playDeath() {
        deathSound.start();
    }

    public void playCheckpoint() {
        scoreCheckpoint.start();
    }


    // ===========================================================
    // onStop
    // ===========================================================

    protected void onStop() {
        super.onStop();
    }
    public void resetSpeed() {
        gameSpeed = Commons.GAME_SPEED;
    }
}