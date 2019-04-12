/*
 * Created by Tareq Islam on 2/16/19 3:11 PM
 *
 *  Last modified 2/16/19 3:11 PM
 */

package com.mti.newviewsv2;

import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NumberActivity extends AppCompatActivity {

    public static String BaseUrl="http://numbersapi.com/";

    TextView numberResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar_number);
        setSupportActionBar(toolbar);

        // toolbar fancy stuff
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       getSupportActionBar().setDisplayShowTitleEnabled(false);

        numberResult=findViewById(R.id.number_result);




      //  Toast.makeText(this, ""+ getIntent().getStringExtra("SEARCH_QUERY"), Toast.LENGTH_SHORT).show();
      //  Toast.makeText(this, ""+ getIntent().getStringExtra("SEARCH_TYPE"), Toast.LENGTH_SHORT).show();

        new LongOperation(getIntent().getStringExtra("SEARCH_QUERY"),"").execute("");
    }

    public boolean onOptionsItemSelected(MenuItem item){
        super.onBackPressed();
        return true;
    }

    private class LongOperation extends AsyncTask<String, Void, String> {

        String param1="";
        String param2="";

        public LongOperation(String param1, String param2) {
            this.param1=param1;
            this.param2=param2;
        }

        @Override
        protected String doInBackground(String... params) {
            try {


                URL url;
                StringBuffer response = new StringBuffer();
                try {
                    url = new URL(BaseUrl+param1+param2);
                } catch (MalformedURLException e) {
                    throw new IllegalArgumentException("invalid url");
                }

                HttpURLConnection conn = null;
                try {
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(false);
                    conn.setDoInput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

                    // handle the response
                    int status = conn.getResponseCode();
                    if (status != 200) {
                        throw new IOException("Post failed with error code " + status);
                    } else {
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String inputLine;
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }

                    //Here is your json in string format
                return response.toString();
                }

            } catch (Exception e) {
                System.out
                        .println("MainActivity.LongOperation.doInBackground()"+e);

            }

              return null;

        }

        @Override
        protected void onPostExecute(String result) {

            numberResult.setText(result);
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}


