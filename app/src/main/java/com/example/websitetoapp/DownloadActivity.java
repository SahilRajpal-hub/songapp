package com.example.websitetoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.websitetoapp.mySongsDownload.mySongsDownload;

public class DownloadActivity extends AppCompatActivity {
        private WebView webView;
        private Button download;
        private ProgressBar progressBar;

        @Override
        public void onBackPressed() {
            if(webView.canGoBack()){
                webView.goBack();
            }else {
                super.onBackPressed();
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_download);

            download= findViewById(R.id.download);
            webView = findViewById(R.id.webview);
            progressBar = findViewById(R.id.progressBar);
            websettings();
            webView.loadUrl("https://www.youtube.com");

            webView.setWebViewClient(new mySongsDownload(progressBar));


            download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("url",webView.getUrl());
                    clipboardManager.setPrimaryClip(clip);
                    webView.loadUrl("https://ytmp3.cc/en13/");
                }
            });

            webView.setDownloadListener(new DownloadListener() {
                @Override
                public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);

                }
            });


        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        public void websettings(){
            try {
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setLoadsImagesAutomatically(true);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webSettings.setBuiltInZoomControls(false);
                webSettings.setMediaPlaybackRequiresUserGesture(true);
                webSettings.setPluginState(WebSettings.PluginState.ON);
            }catch (Exception e){
                Toast.makeText(this, "error : " + e, Toast.LENGTH_SHORT).show();
            }
        }

}
