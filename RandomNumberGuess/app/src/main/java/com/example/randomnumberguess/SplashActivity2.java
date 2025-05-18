package com.example.randomnumberguess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity2 extends AppCompatActivity {
    private static final int duration=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent gotoActivity=new Intent(SplashActivity2.this , MainActivity.class);
                SplashActivity2.this.startActivity(gotoActivity);
                SplashActivity2.this.finish();
            }
        },duration);
    }
}