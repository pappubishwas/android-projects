package com.example.buttondemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void gotoNewActivity(View view) {
        Toast.makeText(MainActivity.this, "Button is clicked properly", Toast.LENGTH_SHORT).show();
        Intent gotoActivity=new Intent(MainActivity.this,MainActivity2.class);
        startActivity(gotoActivity);
    }
}