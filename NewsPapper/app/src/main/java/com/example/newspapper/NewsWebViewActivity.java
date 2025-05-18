package com.example.newspapper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class NewsWebViewActivity extends AppCompatActivity {

    WebView webView;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_web_view);

        webView=findViewById(R.id.webview);

        Bundle data=getIntent().getExtras();

        if(data.containsKey("URL1")){
            url=data.getString("URL1");
        }
        webView.loadUrl(url);
    }
}