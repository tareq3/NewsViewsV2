/*
 * Created by Tareq Islam on 2/15/19 4:57 PM
 *
 *  Last modified 2/15/19 4:57 PM
 */

package com.mti.newviewsv2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mti.newviewsv2.adapter.ListItemAdapterTech;
import com.mti.newviewsv2.api.ApiClient;
import com.mti.newviewsv2.api.ApiServices;
import com.mti.newviewsv2.controller.ApiController;
import com.mti.newviewsv2.model.Article;
import com.mti.newviewsv2.model.Tech;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/***
 * Created by mtita on 15,February,2019.
 */
public class HomeActivityRecyclerFragment1 extends Fragment implements ApiController.OnTechDataLoadCompleteListener, ListItemAdapterTech.ItemClickListener {
    RecyclerView mRecyclerView;


    ListItemAdapterTech listItemAdapter;

    List<Article> techArticleList = new ArrayList<>();

    Context mContext;


    private OnDataSetChangedListener mOnDataSetChangedListener;

    public HomeActivityRecyclerFragment1() {
    }

    public static HomeActivityRecyclerFragment1 newInstance() {
        HomeActivityRecyclerFragment1 fragment = new HomeActivityRecyclerFragment1();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.home_body_recycler_fragment_1, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof OnDataSetChangedListener) {

            mOnDataSetChangedListener = (OnDataSetChangedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDataSetChangedListener");
        }


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView = getView().findViewById(R.id.first_recyclerview);

        mRecyclerView.setHasFixedSize(true);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getView().getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        /*for fixing scroll issue*/
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(newState!=0){
                    if(!HomeActivity.mSwipeRefreshLayout.isRefreshing())  HomeActivity.mSwipeRefreshLayout.setEnabled(false);
                }else{
                    HomeActivity.mSwipeRefreshLayout.setEnabled(true);
                }
            }
        });


        listItemAdapter = new ListItemAdapterTech(mContext, techArticleList, this); //if we don't need to use listener can use null instead of this

        mRecyclerView.setAdapter(listItemAdapter);

        loadData();
    }

    public void loadData(){

       ApiController.refresh();

        new ApiController(this).loadTechData();
        new ApiController(this).loadTechRadarData();
        new ApiController(this).loadTechWebData();
        new ApiController(this).loadTechHackerData();
        new ApiController(this).loadTechCryptoData();

    }




    @Override
    public void onDetach() {
        super.onDetach();
        mOnDataSetChangedListener = null;
    }

    @Override
    public void onTechDataLoadCompleted(List<Article> techArticleList) {

    //    Toast.makeText(getContext(), "LAAL "+techArticleList.size(), Toast.LENGTH_SHORT).show();

        this.techArticleList=techArticleList;
        listItemAdapter.updateAdapter((ArrayList<Article>) techArticleList);


        listItemAdapter.notifyDataSetChanged();

        mOnDataSetChangedListener.onDataUpdate(true);
        //Notify Data updated on Activity
       // mOnDataSetChangedListener.onDataUpdate(true);
    }

    @Override
    public void onClick(View view, int position, boolean isLongClick) {
        Toast.makeText(mContext, ""+ techArticleList.get(position).getUrlToImage(), Toast.LENGTH_SHORT).show();

        // Construct an Intent as normal
        Intent intent = new Intent(mContext, DetailActivity.class);
        intent.putExtra("IMAGE_URL", techArticleList.get(position).getUrlToImage());
        intent.putExtra("TITLE",techArticleList.get(position).getTitle());
        intent.putExtra("DETAIL",techArticleList.get(position).getContent());
        intent.putExtra("AUTHOR",techArticleList.get(position).getAuthor());
        intent.putExtra("SRC",techArticleList.get(position).getSource().getName());
        intent.putExtra("DATE",techArticleList.get(position).getPublishedAt());
        intent.putExtra("URL",techArticleList.get(position).getUrl());

        // BEGIN_INCLUDE(start_activity)

        ActivityOptionsCompat activityOptions =  ActivityOptionsCompat.makeSceneTransitionAnimation(
                getActivity(),

                // Now we provide a list of Pair items which contain the view we can transitioning
                // from, and the name of the view it is transitioning to, in the launched activity
                new Pair<View, String>(view.findViewById(R.id.cardImg), DetailActivity.VIEW_NAME_HEADER_IMAGE
                ),

                new Pair<View, String>(view.findViewById(R.id.card_title), DetailActivity.VIEW_NAME_HEADER_TITLE
                )
        );
// Now we can start the Activity, providing the activity options as a bundle
        ActivityCompat.startActivity(mContext, intent, activityOptions.toBundle());
        // END_INCLUDE(start_activity)
    }


    /**
     * This interface must be implemented by activities that contain this fragment to allow an
     * interaction in this fragment to be communicated to the activity and potentially other
     * fragments contained in that activity.
     * <p>
     * See the Android Training lesson <a href= "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface OnDataSetChangedListener {
        void onDataUpdate(boolean completed);
    }
}
