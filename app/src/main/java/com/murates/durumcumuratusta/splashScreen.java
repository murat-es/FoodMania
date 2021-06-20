package com.murates.durumcumuratusta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class splashScreen extends AppCompatActivity {
    private ImageView imageView;
    private static int time=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imageView=findViewById(R.id.imageViewSplash);

        Animation animation= AnimationUtils.loadAnimation(this,R.anim.animation);
        animation.setDuration(1500);
        imageView.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(splashScreen.this,sign_in.class);
                startActivity(intent);
                finish();
            }
        },time);
    }
}