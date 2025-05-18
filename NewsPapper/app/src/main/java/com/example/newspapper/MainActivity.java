package com.example.newspapper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button paButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paButton=findViewById(R.id.pAloBtn);
        paButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotToProthomAloWebView=new Intent(MainActivity.this,NewsWebViewActivity.class);
                gotToProthomAloWebView.putExtra("URL1","https://java2blog.com/android-webview-example/");
                startActivity(gotToProthomAloWebView);
            }
        });
    }
}