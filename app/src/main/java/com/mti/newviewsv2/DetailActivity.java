/*
 * Created by Tareq Islam on 2/17/19 1:03 AM
 *
 *  Last modified 2/17/19 1:03 AM
 */

package com.mti.newviewsv2;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mti.newviewsv2.untility.GlideApp;

import retrofit2.http.Url;

public class DetailActivity extends AppCompatActivity {

    // Extra name for the ID parameter
    public static final String EXTRA_PARAM_ID = "detail:_id";

    // View name of the header image. Used for activity scene transitions
    public static final String VIEW_NAME_HEADER_IMAGE = "detail:header:image";

    // View name of the header title. Used for activity scene transitions
    public static final String VIEW_NAME_HEADER_TITLE = "detail:header:title";

    private ImageView mHeaderImageView;
    private TextView mHeaderTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Hide status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);

        // toolbar fancy stuff
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Card Details");


        mHeaderImageView =  findViewById(R.id.imageview_header);
        mHeaderTitle =findViewById(R.id.textview_title);
        ((TextView) findViewById(R.id.textview_content)).setText(getIntent().getStringExtra("DETAIL"));
        ((TextView) findViewById(R.id.textview_author)).setText(getIntent().getStringExtra("AUTHOR"));
        ((TextView) findViewById(R.id.textview_src)).setText(getIntent().getStringExtra("SRC"));
        ((TextView) findViewById(R.id.textview_date)).setText(getIntent().getStringExtra("DATE"));



        Toast.makeText(this, ""+ getIntent().getStringExtra("TITLE"), Toast.LENGTH_SHORT).show();

        // BEGIN_INCLUDE(detail_set_view_name)
        /**
         * Set the name of the view's which will be transition to, using the static values above.
         * This could be done in the layout XML, but exposing it via static variables allows easy
         * querying from other Activities
         */
        ViewCompat.setTransitionName(mHeaderImageView, VIEW_NAME_HEADER_IMAGE);
        ViewCompat.setTransitionName(mHeaderTitle, VIEW_NAME_HEADER_TITLE);
        // END_INCLUDE(detail_set_view_name)


        loadItem();

        showAddItemDialog(this);
    }

    private void showAddItemDialog(Context c) {
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Attention")
                .setMessage("Would you like to open in a Web Browser?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //open the url in web browser like chrome
                        openWebPage(getIntent().getStringExtra("URL"));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //open the url in web view
                        Intent intent= new Intent(DetailActivity.this, MyWebActivity.class);
                        intent.putExtra("URL", getIntent().getStringExtra("URL"));
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                })
                .create();
        dialog.show();
    }

    public void openWebPage(String url) {
        try {
            Uri webpage = Uri.parse(url);
            Intent myIntent = new Intent(Intent.ACTION_VIEW, webpage);
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No application can handle this request. Please install a web browser or check your URL.",  Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void loadItem() {
        // Set the title TextView to the item's name and author
        mHeaderTitle.setText(getIntent().getStringExtra("TITLE"));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if ( (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP )&& addTransitionListener() ) {
                // If we're running on Lollipop and we have added a listener to the shared element
                // transition, load the thumbnail. The listener will load the full-size image when
                // the transition is complete.
                loadFullSizeImage();
            } else {
                // If all other cases we should just load the full-size image now
                loadFullSizeImage();
            }
        }
    }


    public boolean onOptionsItemSelected(MenuItem item){
        super.onBackPressed();
        return true;
    }

    /**
     * Load the item's thumbnail image into our {@link ImageView}.
     */
    private void loadThumbnail() {
        GlideApp.with(mHeaderImageView.getContext())
                .load(getIntent().getStringExtra("IMAGE_URL"))
                .placeholder(mHeaderImageView.getDrawable())
                .into(mHeaderImageView);
    }

    /**
     * Load the item's full-size image into our {@link ImageView}.
     */
    private void loadFullSizeImage() {
        GlideApp.with(mHeaderImageView.getContext())
                .load(getIntent().getStringExtra("IMAGE_URL") )
                .placeholder(mHeaderImageView.getDrawable())
                .into(mHeaderImageView);
    }

    /**
     * Try and add a {@link Transition.TransitionListener} to the entering shared element
     * {@link Transition}. We do this so that we can load the full-size image after the transition
     * has completed.
     *
     * @return true if we were successful in adding a listener to the enter transition
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private boolean addTransitionListener() {
        final Transition transition = getWindow().getSharedElementEnterTransition();

        if (transition != null) {
            // There is an entering shared element transition so add a listener to it
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                transition.addListener(new Transition.TransitionListener() {
                    @Override
                    public void onTransitionEnd(Transition transition) {
                        // As the transition has ended, we can now load the full-size image
                     //   loadFullSizeImage();

                        // Make sure we remove ourselves as a listener

                        transition.removeListener(this);

                    }

                    @Override
                    public void onTransitionStart(Transition transition) {
                        // No-op
                    }

                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onTransitionCancel(Transition transition) {
                        // Make sure we remove ourselves as a listener
                        transition.removeListener(this);
                    }

                    @Override
                    public void onTransitionPause(Transition transition) {
                        // No-op
                    }

                    @Override
                    public void onTransitionResume(Transition transition) {
                        // No-op
                    }
                });
            }
            return true;
        }

        // If we reach here then we have not added a listener
        return false;
    }
}
