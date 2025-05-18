package com.example.androidwidgettablayoutimagesilder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {

    WebView myWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        myWebView=findViewById(R.id.webView);
        myWebView.loadUrl("https://www.prothomalo.com/");
    }
}