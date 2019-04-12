/*
 * Created by Tareq Islam on 2/15/19 4:01 PM
 *
 *  Last modified 2/14/19 8:36 PM
 */

package com.mti.newviewsv2.api;


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

import javax.annotation.Generated;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {
       @GET("/v2/top-headlines")
       Single<Tech> getTechArticles(@Query("sources") String sources, @Query("apiKey") String apiKey);

    @GET("/v2/top-headlines")
    Single<Tech> getTechRadarArticles(@Query("sources") String sources, @Query("apiKey") String apiKey);

       @GET("/v2/top-headlines")
       Single<Tech> getTechWebArticles(@Query("sources") String sources, @Query("apiKey") String apiKey);

    @GET("/v2/top-headlines")
    Single<Tech> getTechHackerArticles(@Query("sources") String sources, @Query("apiKey") String apiKey);


    @GET("/v2/top-headlines")
    Single<Tech> getTechCryptoArticles(@Query("sources") String sources, @Query("apiKey") String apiKey);



    @GET("/v2/top-headlines")
    Single<Business> getBusinessArticles(@Query("country") String country, @Query("category") String category, @Query("apiKey") String apiKey);

    @GET("/v2/top-headlines")
    Single<Business> getBusinessInsiderArticles( @Query("sources") String sources, @Query("apiKey") String apiKey);

    @GET("/v2/top-headlines")
    Single<Business> getCNBCArticles( @Query("sources") String sources, @Query("apiKey") String apiKey);

    @GET("/v2/top-headlines")
    Single<Business> getFinancialTimesArticles(@Query("sources") String sources, @Query("apiKey") String apiKey);

    @GET("/v2/top-headlines")
    Single<Business> getFortuneArticles( @Query("sources") String sources, @Query("apiKey") String apiKey);


    //region Hints for retrofit calls
/*    So, using this route the retrofit will generate the following URL:
    http://api.themoviedb.org/3/movie/top_rated?api_key=12345678910111213

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    //for variable in path
      @GET("movie/{id}")
    Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);


    HERE are some example of annotation in retrofit2:
      Take a look to other annotations:

@Path – variable substitution for the API endpoint. For example movie id will be swapped for{id} in the URL endpoint.

@Query – specifies the query key name with the value of the annotated parameter.

@Body – payload for the POST call

@Header – specifies the header with the value of the annotated parameter
    */
    //endregion

}
