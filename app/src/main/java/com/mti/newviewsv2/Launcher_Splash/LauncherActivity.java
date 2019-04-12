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
 * Created by Tareq Islam on 7/25/18 5:27 PM
 *
 *  Last modified 7/25/18 4:57 PM
 */

/*
 * Created by Tareq Islam on 7/25/18 4:55 PM
 *
 *  Last modified 7/25/18 4:55 PM
 */

package com.mti.newviewsv2.Launcher_Splash;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/***
 * Created by Tareq on 25,July,2018.
 */
public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Todo!:1st way easy one:  Start Activity
        /*From launcher we can even start MainActivity without any transiction anim*/
       // startActivity(new Intent(this,MainActivity.class));
       // finish();

        //Todo: 2nd way reomended one: Using a Launcher_Extender Activity that extends launcer icon preiod with anim transiction
        startActivity(new Intent(this,Launcher_Extender.class));
        finish();
    }
}
