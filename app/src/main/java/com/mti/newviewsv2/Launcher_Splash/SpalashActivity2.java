/*
 * Created by Tareq Islam on 2/18/19 11:23 PM
 *
 *  Last modified 2/18/19 11:23 PM
 */

package com.mti.newviewsv2.Launcher_Splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AnimationUtils;

import com.mti.newviewsv2.HomeActivity;
import com.mti.newviewsv2.Launcher_Splash.IntoActivity.IntroActivity;
import com.mti.newviewsv2.R;

public class SpalashActivity2 extends AppCompatActivity {

    private static final int LAUNCHER_PREIOD = 2000; //2 sec
    private final Handler mHandler = new Handler();
    private final SpalashActivity2.Launcher mLauncher = new SpalashActivity2.Launcher();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalash2);
        //todo: initial transition animation


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


//Shared pref for storing data for AppLifeTime
            final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            // Check if we need to display our OnboardingFragment
            if (!sharedPreferences.getBoolean(
                    "virgin2", false)) {

                //Todo!1: Change pref value to true so it doesn't play again
                SharedPreferences.Editor sharedPreferencesEditor =
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                sharedPreferencesEditor.putBoolean("virgin2", true);
                sharedPreferencesEditor.apply();

                startActivity(new Intent(this, HomeActivity.class)); //Here set the next activity
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        }
    }

    private class Launcher implements Runnable {
        @Override
        public void run() {
            launch();
        }
    }
}