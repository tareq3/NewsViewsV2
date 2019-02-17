/*
 * Created by Tareq Islam on 2/15/19 6:52 PM
 *
 *  Last modified 2/15/19 6:51 PM
 */

/*
 * Created by Tareq Islam on 2/15/19 4:57 PM
 *
 *  Last modified 2/15/19 4:57 PM
 */

package com.mti.newviewsv2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.mti.newviewsv2.model.Business;
import com.mti.newviewsv2.model.Tech;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/***
 * Created by mtita on 15,February,2019.
 */
public class HomeActivityRecyclerFragment2 extends Fragment implements ApiController.OnBusinessDataLoadCompleteListener {
    RecyclerView mRecyclerView;


    ListItemAdapterTech listItemAdapter;

    List<Article> businessArticleList = new ArrayList<>();
    Context mContext;


    private OnDataSetChangedListener mOnDataSetChangedListener;

    public HomeActivityRecyclerFragment2() {
    }

    public static HomeActivityRecyclerFragment2 newInstance() {
        HomeActivityRecyclerFragment2 fragment = new HomeActivityRecyclerFragment2();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.home_body_recycler_fragment_2, container, false);
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
        mRecyclerView = getView().findViewById(R.id.second_recyclerview);

        mRecyclerView.setHasFixedSize(true);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getView().getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        /*for fixing scroll issue*/
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(newState!=0){
                    if(!HomeActivity.mSwipeRefreshLayout.isRefreshing()) HomeActivity.mSwipeRefreshLayout.setEnabled(false);
                }else{
                    HomeActivity.mSwipeRefreshLayout.setEnabled(true);
                }
            }
        });


        listItemAdapter = new ListItemAdapterTech(mContext, businessArticleList, null); //if we don't need to use listener can use null instead of this

        mRecyclerView.setAdapter(listItemAdapter);

        loadData();
    }
    public void loadData(){
        ApiController.refresh();

        new ApiController(this).loadBusinessData();
        new ApiController(this).loadBusinessInsiderData();
        new ApiController(this).loadCNBCData();
        new ApiController(this).loadFinanceData();
        new ApiController(this).loadFortuneData();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnDataSetChangedListener = null;
    }

    @Override
    public void onBusinessDataLoadCompleted(List<Article> businessArticleList) {

       // Toast.makeText(mContext, "business"+businessArticleList.size(), Toast.LENGTH_SHORT).show();

        this.businessArticleList=businessArticleList;
        listItemAdapter.updateAdapter((ArrayList<Article>) businessArticleList);


        listItemAdapter.notifyDataSetChanged();

        mOnDataSetChangedListener.onDataUpdate2(true);
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
        void onDataUpdate2(boolean completed);
    }
}
