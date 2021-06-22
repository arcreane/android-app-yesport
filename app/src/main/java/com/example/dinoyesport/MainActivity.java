package com.example.dinoyesport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {


    private Display current_screen;
    private GameView current_gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        current_gameView = new GameView(this);

        // hide the Top title bar
        getSupportActionBar().hide();

        // resquest landscape orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(current_gameView);
        current_screen = getWindowManager().getDefaultDisplay();

        current_gameView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        Log.d("pressed", "yes");
                        break;
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        Log.d("pressed", "no");
                        break;
                }
                return true;
            }
        });

    }
    public Display getCurrent_screen() { return this.current_screen; }
    public GameView getCurrent_gameView() { return current_gameView; }

}