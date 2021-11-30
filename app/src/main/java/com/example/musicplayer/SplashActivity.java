package com.example.musicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIMEOUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceStae) {
        super.onCreate(savedInstanceStae);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splash_activity);
        //add animation to the imageview

        Animation fadeOut = new AlphaAnimation(1,0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(500);
        fadeOut.setDuration(2500);
        ImageView imageView = findViewById(R.id.imgView);

        imageView.setAnimation(fadeOut);

        //activity stays for a period time

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //use intent to go to login activity
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN_TIMEOUT);
    }
}
