package com.example.widgetexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ToggleActivity extends AppCompatActivity {

    ToggleButton toggleButton1,toggleButton2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toggle);

        toggleButton1=findViewById(R.id.tb1);
        toggleButton2=findViewById(R.id.tb2);
    }

    public void Submit(View view) {
        StringBuilder result=new StringBuilder();
        result.append("ToggleButton1 : ").append(toggleButton1.getText());
        result.append("\nToggleButton2 : ").append(toggleButton2.getText());

        Toast.makeText(getApplicationContext(),result.toString(),Toast.LENGTH_SHORT).show();
    }
}