/*
 * Created by Tareq Islam on 2/17/19 3:12 AM
 *
 *  Last modified 2/17/19 3:12 AM
 */

package com.mti.newviewsv2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_web);

        // Hide status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

      /*  android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar_web);
        setSupportActionBar(toolbar);

        // toolbar fancy stuff
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Web View");*/


        WebView myWebView = (WebView) findViewById(R.id.webview);

        myWebView.loadUrl(getIntent().getStringExtra("URL"));



        myWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                ( (View) findViewById(R.id.loading_view)).setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        super.onBackPressed();
        return true;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
