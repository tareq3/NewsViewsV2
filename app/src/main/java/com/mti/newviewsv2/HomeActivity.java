/*
 * Created by Tareq Islam on 2/15/19 2:57 PM
 *
 *  Last modified 2/15/19 2:54 PM
 */

package com.mti.newviewsv2;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.SearchRecentSuggestions;
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

import com.mti.newviewsv2.utility.ConnectivityHelper;
import com.mti.newviewsv2.utility.MySuggestionProvider;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

public class HomeActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,HomeActivityRecyclerFragment1.OnDataSetChangedListener,HomeActivityRecyclerFragment2.OnDataSetChangedListener, HomeNavDrawerFragment.OnNavDrawerItemClickListener {

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

        mMenuFragment.vNavigation.setCheckedItem(R.id.nav_drawer_home);

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
