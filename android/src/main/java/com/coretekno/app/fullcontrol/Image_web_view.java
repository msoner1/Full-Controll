package com.coretekno.app.fullcontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;


public class Image_web_view extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_web_view);

        WebView image_web = (WebView) findViewById(R.id.webView_image);
        ImageView back = (ImageView) findViewById(R.id.back_button);
        intent = getIntent();

        image_web.loadUrl(intent.getStringExtra("url"));
        image_web.getSettings().setLoadWithOverviewMode(true);
        image_web.getSettings().setBuiltInZoomControls(true);
        image_web.getSettings().setSupportZoom(true);
        image_web.getSettings().setUseWideViewPort(true);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent_start = new Intent(this,Image_view.class);
        intent_start.putExtra("which_action",intent.getStringExtra("which_action"));
        startActivity(intent_start);
    }
}
