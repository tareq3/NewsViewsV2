/*
 * Created by Tareq Islam on 2/15/19 2:57 PM
 *
 *  Last modified 2/15/19 2:54 PM
 */

package com.mti.newviewsv2;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mti.newviewsv2.utility.ConnectivityHelper;
import com.mti.newviewsv2.utility.HAWK_KEYS;
import com.mti.newviewsv2.utility.MySuggestionProvider;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,HomeActivityRecyclerFragment1.OnDataSetChangedListener,HomeActivityRecyclerFragment2.OnDataSetChangedListener, HomeNavDrawerFragment.OnNavDrawerItemClickListener {

    //member variable for firebase Auth
    private static final int RC_SIGN_IN = 1 ; //activity Result tag

    FirebaseAuth mFireBaseAuth;

    FirebaseAuth.AuthStateListener mAuthStateListener; //for sign in auth state like showing sign in option or closing options


    public static SwipeRefreshLayout mSwipeRefreshLayout;
FlowingDrawer mDrawer;

  Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_app_bar);

        mContext=this;
      /*  android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
*/
        // toolbar fancy stuff



// Hide status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /*For showing history from content provider*/
        Intent intent  = getIntent();

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
         //    suggestions.clearHistory();

            suggestions.saveRecentQuery(query, null);
        }


        mSwipeRefreshLayout = findViewById(R.id.swiperefresh);

        // BEGIN_INCLUDE (change_colors)
        // Set the color scheme of the SwipeRefreshLayout by providing 4 color resource ids
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.swipe_color_1, R.color.swipe_color_2,
                R.color.swipe_color_3, R.color.swipe_color_4);
        // END_INCLUDE (change_colors)
        mSwipeRefreshLayout.setOnRefreshListener(this);




        initRecyclerViewFragment(savedInstanceState);

        initNavigationDrawer();
        initFirebaseAuth();
    }


    private void initFirebaseAuth() {

        //FirebaseApp.initializeApp(this);

        mFireBaseAuth=FirebaseAuth.getInstance();

        mAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user!=null){
                    //user is signed in

                 //   Toast.makeText(HomeActivity.this, "User is Signed in", Toast.LENGTH_SHORT).show();

                    //On user found update navigation drawer
                   mMenuFragment.updateProfile();
                }else{
                    //User is not signed in

                    //Todo: We have to set the mechanism for sign in the user

                    // Choose authentication providers
                    List<AuthUI.IdpConfig> providers = Arrays.asList(
                            new AuthUI.IdpConfig.EmailBuilder().build(),
                           new AuthUI.IdpConfig.FacebookBuilder().build(), //this is the only line needed for facebook sign in
                            new AuthUI.IdpConfig.GoogleBuilder().build()

                    );

// Create and launch sign-in intent
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false) //setting this true adds a lot complexity
                                    .setAvailableProviders(providers)
                                    .setLogo(R.mipmap.ic_launcher) //for setting our logo, Optional
                                    .setTheme(R.style.FireBaseGreenTheme) //optional
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };




    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
          /*      Toast.makeText(mContext, "Name"+ user.getDisplayName()
                        +"\n Email" + user.getEmail()
                        +"\n Phone" + user.getPhoneNumber()
                        + "\n Photourl" + user.getPhotoUrl(), Toast.LENGTH_LONG).show();
*/
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

                pref.edit().putString(HAWK_KEYS.USER_KEYS.user_name.toString(),user.getDisplayName()).commit();
                pref.edit().putString(HAWK_KEYS.USER_KEYS.user_email.toString(),user.getEmail()).commit();

             if (user.getPhotoUrl()!=null)   pref.edit().putString(HAWK_KEYS.USER_KEYS.user_photo_url.toString(),user.getPhotoUrl().toString()).commit();


                //Todo Log the HAWK DATAS

                Log.d("HAWK" +getClass().getName(),
                        ""+
                                pref.getString(HAWK_KEYS.USER_KEYS.user_name.toString(),"")+
                                        pref.getString(HAWK_KEYS.USER_KEYS.user_email.toString(),"")+

                                                pref.getString(HAWK_KEYS.USER_KEYS.user_photo_url.toString(),"")

                );

                //for Sign up into user table

            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                finish(); //closing App
            }
        }
    }









    private void initNavigationDrawer(){
        mDrawer=findViewById(R.id.drawerlayout);
        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);

        setupToolbar();
        setNavigationDrawer();
    }


    protected void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

       // getSupportActionBar().setTitle(R.string.app_name);
        //Setting up navigation drawer taggle button
        toolbar.setNavigationIcon(R.drawable.ic_menu_black);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                mDrawer.toggleMenu();
            }
        });

        mDrawer.setOnDrawerStateChangeListener(new ElasticDrawer.OnDrawerStateChangeListener() {
            @Override
            public void onDrawerStateChange(int oldState, int newState) {
                if (newState == ElasticDrawer.STATE_OPEN) {
                    Log.i("MainActivity", "Drawer STATE_CLOSED");

                  //  mINavigationDrawerUpdate.onNavDrawerOpen();
                }
            }

            @Override
            public void onDrawerSlide(float openRatio, int offsetPixels) {
                Log.i("MainActivity", "openRatio=" + openRatio + " ,offsetPixels=" + offsetPixels);
            }
        });

    }

    HomeNavDrawerFragment mMenuFragment;
    private void setNavigationDrawer() {

        FragmentManager fm = getSupportFragmentManager();
         mMenuFragment = (HomeNavDrawerFragment) fm.findFragmentById(R.id.id_container_menu);
        if (mMenuFragment == null) {
            mMenuFragment = HomeNavDrawerFragment.newInstance();
            fm.beginTransaction().add(R.id.id_container_menu, mMenuFragment,"NavigationDrawerFragment").commit();
        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);



        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int i) {

                return false;
            }

            @Override
            public boolean onSuggestionClick(int i) {


                /*Getting search history data on clicking on that and setting query on search field and trigger submit*/
                Cursor cursor= searchView.getSuggestionsAdapter().getCursor();
                cursor.moveToPosition(i);
                String query =cursor.getString(2);//2 is the index of col containing suggestion name.
                searchView.setQuery(query,true);//setting suggestion

                  return true;
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Intent intent=new Intent(HomeActivity.this,NumberActivity.class);
                intent.putExtra("SEARCH_QUERY",query);

                //for adding new suggestion
                SearchRecentSuggestions suggestions = new SearchRecentSuggestions(mContext,
                        MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);

            switch ( checkInputPattern(query)){
                case "number":
                    /*do Number api call*/
                    //following line for adding suggestion on new successful query
                     suggestions.saveRecentQuery(query, null);


                      intent.putExtra("SEARCH_TYPE", "number");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    return true;
                case "date":
                    /*Do date api call*/

                    //following line for adding suggestion on new successful query
                     suggestions.saveRecentQuery(query, null);

                    intent.putExtra("SEARCH_TYPE", "date");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    return true;
                case "not-allowed":
                    /*do nothing*/
                    suggestions.saveRecentQuery(query, null);
                       return true;
            }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {



                return true;
            }
        });

        return true;
    }

    private String checkInputPattern(String query){

        if (query.matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])")) {
        //    Toast.makeText(HomeActivity.this, "Submit-date" + query, Toast.LENGTH_SHORT).show();
            return "date";
        }else if (query.matches("[0-9]+")) {
        //    Toast.makeText(HomeActivity.this, "submit-number" + query, Toast.LENGTH_SHORT).show();
            return "number";
        }else {
            Toast.makeText(HomeActivity.this, "input not allowed", Toast.LENGTH_SHORT).show();
            return "not-allowed";
        }
    }




    HomeActivityRecyclerFragment1 fragmentInstance;

    HomeActivityRecyclerFragment2 fragmentInstance2;

    private void initRecyclerViewFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            fragmentInstance = HomeActivityRecyclerFragment1.newInstance();
            fragmentInstance2=HomeActivityRecyclerFragment2.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.home_body_fragment_1_container, fragmentInstance)
                    .commitNow();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.home_body_fragment_2_container, fragmentInstance2)
                    .commitNow();
        }
    }



    boolean load1=false;
    boolean load2=false;
    @Override
    public void onRefresh() {

        load1=false;
        load2=false;

        fragmentInstance.loadData();
        fragmentInstance2.loadData();

        if (ConnectivityHelper.isConnectedToNetwork(this)) {
            //Show the connected screen

        } else {
            //Show disconnected screen
            mSwipeRefreshLayout.setRefreshing(false);
            Toast.makeText(this, "Make Sure you turned on Internet", Toast.LENGTH_SHORT).show();
        }

    }



    @Override
    public void onDataUpdate(boolean completed) {
        load1=completed;
        if(load1 && load2) mSwipeRefreshLayout.setRefreshing(false);
    }
    @Override
    public void onDataUpdate2(boolean completed) {
        load2=completed;

        if(load1 && load2) mSwipeRefreshLayout.setRefreshing(false);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mFireBaseAuth.addAuthStateListener(mAuthStateListener); //showing sign in options
        mMenuFragment.vNavigation.setCheckedItem(R.id.nav_drawer_home);

    }


    @Override
    protected void onPause() {
        super.onPause();

        if(mAuthStateListener!=null)
            mFireBaseAuth.removeAuthStateListener(mAuthStateListener); //closing sign in options

    }

    @Override
    public void onNavItemClick(MenuItem menuItem) {
        /*Do menu item click actions*/

        switch (menuItem.getItemId()){
            case R.id.nav_drawer_home:
                break;
            case  R.id.nav_drawer_about:

                startActivity(new Intent(this, AboutActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.nav_drawer_exit:
                this.finish();
                System.exit(0);
                break;
        }
    }



}
