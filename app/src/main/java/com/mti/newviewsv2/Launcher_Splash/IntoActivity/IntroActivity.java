/*
 * Created by Tareq Islam on 2/18/19 5:16 PM
 *
 *  Last modified 2/18/19 4:32 PM
 */

/*
 * Created by Tareq Islam on 7/26/18 10:00 PM
 *
 *  Last modified 7/26/18 9:55 PM
 */

package com.mti.newviewsv2.Launcher_Splash.IntoActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import com.mti.newviewsv2.HomeActivity;
import com.mti.newviewsv2.R;

/***
 * Created by Tareq on 25,July,2018.
 */
public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Note here that we DO NOT use setContentView();

        // Add your slide fragments here.
        // AppIntro will automatically generate the dots indicator and buttons

        /*  Here is more than 3 way of creating Slide
            *1. Using custom Fragment
            *2. Using   SampleSlideClass and custom xml
            *3. Using Default AppIntroFragment */


       // addSlide(SampleSlide.newInstance(R.layout.slide_1));

            addSlide(AppIntroFragment.newInstance("Hello!","I am Tareq Islam.", R.drawable.people,getResources().getColor(R.color.slide_bg_1)));
            addSlide(AppIntroFragment.newInstance("Welcome \n to","A place to get every story happening ",R.drawable.news_views,getResources().getColor(R.color.slide_bg_2)));
            addSlide(AppIntroFragment.newInstance( "I hope", "You will enjoy this app",R.drawable.ic_done_white,getResources().getColor(R.color.slide_bg_3)));


        showSkipButton(false);
/*
        // Hide Skip/Done button.
        showSkipButton(false);
        setProgressButtonEnabled(false);*/

    }


    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        //Todo!1: Change pref value to true so it doesn't play again
       SharedPreferences.Editor sharedPreferencesEditor =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        sharedPreferencesEditor.putBoolean("virgin", true);
        sharedPreferencesEditor.apply();

        //Todo!2: start First Activity
        //First Activity
            Intent intentFirst=new Intent(this, HomeActivity.class);
            startActivity(intentFirst);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();

    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}
