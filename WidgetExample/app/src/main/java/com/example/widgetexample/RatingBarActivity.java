package com.example.widgetexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

public class RatingBarActivity extends AppCompatActivity {

    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_bar);
        ratingBar=findViewById(R.id.ratingbar);
    }

    public void submitRating(View view) {
        String rating= String.valueOf(ratingBar.getRating());
        Toast.makeText(getApplicationContext(),rating,Toast.LENGTH_SHORT).show();
    }
}