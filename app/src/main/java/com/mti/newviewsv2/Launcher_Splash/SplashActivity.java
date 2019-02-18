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

package com.mti.newviewsv2.Launcher_Splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.mti.newviewsv2.HomeActivity;

import com.mti.newviewsv2.Launcher_Splash.IntoActivity.IntroActivity;
import com.mti.newviewsv2.R;

//Branch_01
public class SplashActivity extends AppCompatActivity {


    private static final int LAUNCHER_PREIOD = 2000; //2 sec

    private final Handler mHandler   = new Handler();
    private final Launcher mLauncher = new Launcher();

    //layout for transition Animation
    ConstraintLayout l1,l2;

    //Custom animation
    Animation upToDown,downToUP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);


        //todo: initial transition animation

            l1 = (ConstraintLayout) findViewById(R.id.top_part);
            upToDown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
            l1.setAnimation(upToDown);

            l2 = (ConstraintLayout) findViewById(R.id.bottom_part);
            downToUP = AnimationUtils.loadAnimation(this, R.anim.downtoup);
            l2.setAnimation(downToUP);








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
            final SharedPreferences sharedPreferences =    PreferenceManager.getDefaultSharedPreferences(this);
            // Check if we need to display our OnboardingFragment
            if (!sharedPreferences.getBoolean(
                    "virgin", false)) {

                startActivity(new Intent(this, IntroActivity.class)); //Here set the next activity
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                finish();

            }else if(!sharedPreferences.getBoolean(
                    "virgin2", false)) {
                startActivity(new Intent(this, SpalashActivity2.class)); //Here set the next activity
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                finish();

            }else {

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
