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

import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    private Display current_screen;
    private GameView current_gameView;
    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;
    private SensorEventListener gyroscopeEventListener;
    private int gameSpeed = Commons.GAME_SPEED;
    MediaPlayer jumpSound;
    MediaPlayer deathSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        jumpSound = MediaPlayer.create(this, R.raw.dinojump);
        deathSound = MediaPlayer.create(this, R.raw.dinodeath);

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
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(gyroscopeEventListener);

    }

    public void restart() {
        recreate();
    }

    public void playJump() {
        jumpSound.start();
    }

    public void playDeath() {
        deathSound.start();
    }

    public void resetSpeed() {
        gameSpeed = Commons.GAME_SPEED;
    }
}