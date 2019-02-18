/*
 * Created by Tareq Islam on 2/18/19 12:49 AM
 *
 *  Last modified 2/18/19 12:49 AM
 */

package com.mti.newviewsv2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mti.newviewsv2.utility.GlideApp;
import com.mti.newviewsv2.utility.HAWK_KEYS;

/***
 * Created by mtita on 18,February,2019.
 */
public class HomeNavDrawerFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    private Context mContext;
    private OnNavDrawerItemClickListener mOnNavDrawerItemClickListener;

    public HomeNavDrawerFragment() {
    }

    public static HomeNavDrawerFragment newInstance(){
        return new HomeNavDrawerFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;

        if (context instanceof HomeNavDrawerFragment.OnNavDrawerItemClickListener) {

            mOnNavDrawerItemClickListener = (HomeNavDrawerFragment.OnNavDrawerItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnNavDrawerItemClickListener");
        }



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_nav_drawer_fragment, container,
                false);
    }
    public    View headerView;
   public NavigationView vNavigation;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

       vNavigation = getView().findViewById(R.id.vNavigation);
        vNavigation.setNavigationItemSelectedListener(this) ;


        //Settting header view
        headerView=vNavigation.getHeaderView(0);
        setupHeader(headerView);



    }


    private void setupHeader(final View headerView) {
        //Sign out Button
        headerView.findViewById(R.id.sign_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AuthUI.getInstance()
                        .signOut(v.getContext())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                //

                                Toast.makeText(headerView.getContext(), "you are successfully Log out", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }

    public    void updateProfile() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);

        //setting profile name
        ((TextView) headerView.findViewById(R.id.user_name_TextView)).setText(
                prefs.getString(HAWK_KEYS.USER_KEYS.user_name.toString(), "No name") + " \n" +
                        prefs.getString(HAWK_KEYS.USER_KEYS.user_email.toString(), "No name")
        );


        //setting profile pic from HAwk
        GlideApp.with(headerView.getContext())
                .load( prefs.getString(HAWK_KEYS.USER_KEYS.user_photo_url.toString(),""))
                .placeholder(R.drawable.ic_profile_pic)
                .circleCrop()
                .into((ImageView)headerView.findViewById(R.id.profilePic));




    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        //Check and un-check menu item if they are checkable behaviour
        if (menuItem.isCheckable()) {
            if (menuItem.isChecked()) menuItem.setChecked(false);
            else menuItem.setChecked(true);
        }


        mOnNavDrawerItemClickListener.onNavItemClick(menuItem); //sending ItemSelected event to NavRecyclerFragment

        return false;
    }



    public interface OnNavDrawerItemClickListener{
        public void onNavItemClick(MenuItem menuItem);
    }
}
