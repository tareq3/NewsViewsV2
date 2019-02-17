/*
 * Created by Tareq Islam on 2/15/19 7:02 PM
 *
 *  Last modified 2/15/19 7:02 PM
 */

package com.mti.newviewsv2.controller;

import android.util.Log;

import com.mti.newviewsv2.api.ApiClient;
import com.mti.newviewsv2.api.ApiServices;
import com.mti.newviewsv2.model.Article;
import com.mti.newviewsv2.model.Business;
import com.mti.newviewsv2.model.BusinessInsider;
import com.mti.newviewsv2.model.Cnbc;
import com.mti.newviewsv2.model.FinancialTimes;
import com.mti.newviewsv2.model.Fortune;
import com.mti.newviewsv2.model.HackerNews;
import com.mti.newviewsv2.model.Tech;
import com.mti.newviewsv2.model.TechCrypto;
import com.mti.newviewsv2.model.TechRadar;
import com.mti.newviewsv2.model.TheNextWeb;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/***
 * Created by mtita on 15,February,2019.
 */
public class ApiController {

    OnTechDataLoadCompleteListener mOnTechDataLoadCompleteListener;
    OnBusinessDataLoadCompleteListener mOnBusinessDataLoadCompleteListener;

  private static   boolean techCranch=false;
    private static   boolean techRadar=false;
    private static   boolean techWeb=false;
    private static   boolean techHacker=false;
    private static   boolean techCrypto=false;

    private static   boolean business=false;
    private static   boolean businessInsider=false;
    private static   boolean cNBC=false;
    private static   boolean finacialTime=false;
    private static   boolean fortune=false;

  public static void refresh(){
      techRadar=false;
      techCranch=false;
      techWeb=false;
      techHacker=false;
      techCrypto=false;

       business=false;
       businessInsider=false;
      cNBC=false;
      finacialTime=false;
      fortune=false;

      techArticleList.clear();
      businessArticleList.clear();
  }

    public ApiController() {
    }

    public ApiController(OnTechDataLoadCompleteListener onTechDataLoadCompleteListener) {
        mOnTechDataLoadCompleteListener = onTechDataLoadCompleteListener;
    }

    public ApiController(OnBusinessDataLoadCompleteListener onBusinessDataLoadCompleteListener) {
        mOnBusinessDataLoadCompleteListener = onBusinessDataLoadCompleteListener;
    }

   private static   List<Article> techArticleList = new ArrayList<>();
   private static List<Article> businessArticleList = new ArrayList<>();


    /*for loading data using retrofit*/
    public void loadTechData() {
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);

        Call<Tech> call = apiServices.getTechArticles("techcrunch","d63701459ed548309d0fe43690011884");

        call.enqueue(new Callback<Tech>() {


            @Override
            public void onResponse(Call<Tech> call, Response<Tech> response) {
              //  techArticleList.clear();
                techArticleList.addAll(response.body().getArticles());

                techCranch=true;

                if(techCranch && techRadar && techWeb && techHacker && techCrypto)     mOnTechDataLoadCompleteListener.onTechDataLoadCompleted(techArticleList);

               /* Log.d("Tareq", "Number of Article" + response.body().getTotalResults());

                listItemAdapter.updateAdapter((ArrayList<Article>) techArticleList);


                listItemAdapter.notifyDataSetChanged();

                //Notify Data updated on Activity
                mOnDataSetChangedListener.onDataUpdate(true);*/

            }

            @Override
            public void onFailure(Call<Tech> call, Throwable t) {

                Log.d("" +getClass().getName(), "Failed to fetch tech datas from api");
            }
        });
    }


    /*for loading data using retrofit*/
    public void loadTechRadarData() {
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);

        Call<TechRadar> call = apiServices.getTechRadarArticles("techradar","d63701459ed548309d0fe43690011884");

        call.enqueue(new Callback<TechRadar>() {


            @Override
            public void onResponse(Call<TechRadar> call, Response<TechRadar> response) {
                //  techArticleList.clear();
                techArticleList.addAll(response.body().getArticles());

                techRadar=true;
                if(techCranch && techRadar && techWeb && techHacker && techCrypto)     mOnTechDataLoadCompleteListener.onTechDataLoadCompleted(techArticleList);

            }

            @Override
            public void onFailure(Call<TechRadar> call, Throwable t) {

                Log.d("" +getClass().getName(), "Failed to fetch tech datas from api");
            }
        });
    }


    /*for loading data using retrofit*/
    public void loadTechWebData() {
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);

        Call<TheNextWeb> call = apiServices.getTechWebArticles("the-next-web","d63701459ed548309d0fe43690011884");

        call.enqueue(new Callback<TheNextWeb>() {


            @Override
            public void onResponse(Call<TheNextWeb> call, Response<TheNextWeb> response) {
                //  techArticleList.clear();
                techArticleList.addAll(response.body().getArticles());

                techWeb=true;
                if(techCranch && techRadar && techWeb && techHacker && techCrypto)     mOnTechDataLoadCompleteListener.onTechDataLoadCompleted(techArticleList);

            }

            @Override
            public void onFailure(Call<TheNextWeb> call, Throwable t) {

                Log.d("" +getClass().getName(), "Failed to fetch tech datas from api");
            }
        });
    }


    /*for loading data using retrofit*/
    public void loadTechHackerData() {
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);

        Call<HackerNews> call = apiServices.getTechHackerArticles("hacker-news","d63701459ed548309d0fe43690011884");

        call.enqueue(new Callback<HackerNews>() {


            @Override
            public void onResponse(Call<HackerNews> call, Response<HackerNews> response) {
                //  techArticleList.clear();
                techArticleList.addAll(response.body().getArticles());

                techHacker=true;

                if(techCranch && techRadar && techWeb && techHacker && techCrypto)     mOnTechDataLoadCompleteListener.onTechDataLoadCompleted(techArticleList);

            }

            @Override
            public void onFailure(Call<HackerNews> call, Throwable t) {

                Log.d("" +getClass().getName(), "Failed to fetch tech datas from api");
            }
        });
    }

    /*for loading data using retrofit*/
    public void loadTechCryptoData() {
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);

        Call<TechCrypto> call = apiServices.getTechCryptoArticles("crypto-coins-news","d63701459ed548309d0fe43690011884");

        call.enqueue(new Callback<TechCrypto>() {


            @Override
            public void onResponse(Call<TechCrypto> call, Response<TechCrypto> response) {
                //  techArticleList.clear();
                techArticleList.addAll(response.body().getArticles());

                techCrypto=true;

                if(techCranch && techRadar && techWeb && techHacker && techCrypto)     mOnTechDataLoadCompleteListener.onTechDataLoadCompleted(techArticleList);

            }

            @Override
            public void onFailure(Call<TechCrypto> call, Throwable t) {

                Log.d("" +getClass().getName(), "Failed to fetch tech datas from api");
            }
        });
    }







    /*for loading data using retrofit*/
    public void loadBusinessData() {
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);

        Call<Business> call = apiServices.getBusinessArticles("us", "business","d63701459ed548309d0fe43690011884");

        call.enqueue(new Callback<Business>() {


            @Override
            public void onResponse(Call<Business> call, Response<Business> response) {
               // businessArticleList.clear();
              businessArticleList.addAll( response.body().getArticles());

              business=true;

             if(business && businessInsider && cNBC && finacialTime && fortune)   mOnBusinessDataLoadCompleteListener.onBusinessDataLoadCompleted(businessArticleList);



            }

            @Override
            public void onFailure(Call<Business> call, Throwable t) {

                Log.d("" +getClass().getName(), "Failed to fetch tech datas from api");
            }
        });
    }


    /*for loading data using retrofit*/
    public void loadBusinessInsiderData() {
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);

        Call<BusinessInsider> call = apiServices.getBusinessInsiderArticles("business-insider","d63701459ed548309d0fe43690011884");

        call.enqueue(new Callback<BusinessInsider>() {


            @Override
            public void onResponse(Call<BusinessInsider> call, Response<BusinessInsider> response) {
                // businessArticleList.clear();
                businessArticleList.addAll( response.body().getArticles());

                businessInsider=true;

                if(business && businessInsider && cNBC && finacialTime && fortune)                mOnBusinessDataLoadCompleteListener.onBusinessDataLoadCompleted(businessArticleList);



            }

            @Override
            public void onFailure(Call<BusinessInsider> call, Throwable t) {

                Log.d("" +getClass().getName(), "Failed to fetch tech datas from api");
            }
        });
    }

    /*for loading data using retrofit*/
    public void loadCNBCData() {
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);

        Call<Cnbc> call = apiServices.getCNBCArticles( "cnbc","d63701459ed548309d0fe43690011884");

        call.enqueue(new Callback<Cnbc>() {


            @Override
            public void onResponse(Call<Cnbc> call, Response<Cnbc> response) {
                // businessArticleList.clear();
                Log.d("" +getClass().getName(), ""+response.body().getArticles());

                businessArticleList.addAll( response.body().getArticles());

                cNBC=true;

                if(business && businessInsider && cNBC && finacialTime && fortune)                mOnBusinessDataLoadCompleteListener.onBusinessDataLoadCompleted(businessArticleList);



            }

            @Override
            public void onFailure(Call<Cnbc> call, Throwable t) {

                Log.d("" +getClass().getName(), "Failed to fetch tech datas from api");
            }
        });
    }
    /*for loading data using retrofit*/
    public void loadFinanceData() {
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);

        Call<FinancialTimes> call = apiServices.getFinancialTimesArticles( "financial-times","d63701459ed548309d0fe43690011884");

        call.enqueue(new Callback<FinancialTimes>() {


            @Override
            public void onResponse(Call<FinancialTimes> call, Response<FinancialTimes> response) {
                // businessArticleList.clear();
                businessArticleList.addAll( response.body().getArticles());

                finacialTime=true;

                if(business && businessInsider && cNBC && finacialTime && fortune)     mOnBusinessDataLoadCompleteListener.onBusinessDataLoadCompleted(businessArticleList);



            }

            @Override
            public void onFailure(Call<FinancialTimes> call, Throwable t) {

                Log.d("" +getClass().getName(), "Failed to fetch tech datas from api");
            }
        });
    }
    /*for loading data using retrofit*/
    public void loadFortuneData() {
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);

        Call<Fortune> call = apiServices.getFortuneArticles( "fortune","d63701459ed548309d0fe43690011884");

        call.enqueue(new Callback<Fortune>() {


            @Override
            public void onResponse(Call<Fortune> call, Response<Fortune> response) {
                // businessArticleList.clear();
                businessArticleList.addAll( response.body().getArticles());

                fortune=true;

                if(business && businessInsider && cNBC && finacialTime && fortune)     mOnBusinessDataLoadCompleteListener.onBusinessDataLoadCompleted(businessArticleList);



            }

            @Override
            public void onFailure(Call<Fortune> call, Throwable t) {

                Log.d("" +getClass().getName(), "Failed to fetch tech datas from api");
            }
        });
    }

    public interface OnTechDataLoadCompleteListener{
        public void onTechDataLoadCompleted(List<Article> techArticleList);
    }
    public interface OnBusinessDataLoadCompleteListener{
        public void onBusinessDataLoadCompleted(List<Article> businessArticleList);
    }
}
