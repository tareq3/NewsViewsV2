/*
 * Created by Tareq Islam on 2/15/19 9:22 PM
 *
 *  Last modified 2/15/19 8:03 PM
 */

/*
 * Created by Tareq Islam on 2/15/19 8:03 PM
 *
 *  Last modified 2/15/19 8:03 PM
 */

package com.mti.newviewsv2.untility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/***
 * Created by mtita on 15,February,2019.
 */
public class ConnectivityHelper {
    public static boolean isConnectedToNetwork(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean isConnected = false;
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            isConnected = (activeNetwork != null) && (activeNetwork.isConnectedOrConnecting());
        }

        return isConnected;
    }
}