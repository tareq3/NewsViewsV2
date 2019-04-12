/*
 * Created by Tareq Islam on 2/18/19 4:53 PM
 *
 *  Last modified 2/18/19 4:32 PM
 */

/*
 * Created by Tareq Islam on 7/26/18 10:00 PM
 *
 *  Last modified 7/26/18 9:55 PM
 */

/*
 * Created by Tareq Islam on 7/25/18 3:06 PM
 *
 *  Last modified 7/25/18 3:06 PM
 */

package com.mti.newviewsv2.Launcher_Splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mti.newviewsv2.R;


/***
 * Created by Tareq on 25,July,2018.
 */
public class Launcher_Extender extends AppCompatActivity {

    //This activity extends launcher icon preiod it just show the same layout as laucher has


    private static final int LAUNCHER_PREIOD = 500; //2 sec

    private final Handler mHandler   = new Handler();
    private final Launcher mLauncher = new Launcher();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // Make sure this is before calling super.onCreate
        setTheme(R.style.Launcher);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher_ext_layout);



    }


    @Override
    protected void onStart() {
        super.onStart();

        mHandler.postDelayed(mLauncher, LAUNCHER_PREIOD);
    }

    @Override
    protected void onStop() {
        mHandler.removeCallbacks(mLauncher);
        super.onStop();
    }

    private void launch() {
        if (!isFinishing()) {
            startActivity(new Intent(this, SplashActivity.class)); //Here set the next activity
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }
    }

    private class Launcher implements Runnable {
        @Override
        public void run() {
            launch();
        }
    }
}
