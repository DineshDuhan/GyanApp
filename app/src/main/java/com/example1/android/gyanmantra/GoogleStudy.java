package com.example1.android.gyanmantra;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class GoogleStudy extends AppCompatActivity {
      public String googlelink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_study);


        getSupportActionBar().setTitle("Study Material");
     //   getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        googlelink = getIntent().getStringExtra("GoogleLink");
        Toast.makeText(GoogleStudy.this, ""+googlelink, Toast.LENGTH_SHORT).show();

        WebView webview = (WebView)findViewById(R.id.mywebview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl(googlelink);
    }
}
