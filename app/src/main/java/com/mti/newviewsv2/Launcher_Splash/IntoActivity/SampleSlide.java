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


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/***
 * Created by Tareq on 25,July,2018.
 */
public class SampleSlide extends Fragment {

    private static final String ARG_LAYOUT_RES_ID = "layoutResId";
    private int layoutResId;

    public static SampleSlide newInstance(int layoutResId) {
        SampleSlide sampleSlide = new SampleSlide();

        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_RES_ID, layoutResId);
        sampleSlide.setArguments(args);

        return sampleSlide;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(ARG_LAYOUT_RES_ID)) {
            layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(layoutResId, container, false);
    }
}