package com.solariswu.gdrive.services;


import com.solariswu.gdrive.models.ShroudedData;
import com.solariswu.gdrive.models.YoutubeData;

import java.util.HashMap;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yungang wu on 16/8/15.
 *
 */

/**
 * Provides the interface for {@link retrofit2.Retrofit} describing the endpoints
 * and responses for the endpoints.
 */

public interface RetrofitService {

    @Headers("Content-Type:application/json")
    @POST("/predict")
    Observable<ShroudedData> postShroudedData(@Body String query);

    @GET("/youtube/v3/search")
    Observable<YoutubeData> getYoutubeData(@Query("type") String type,
                                           @Query("part") String part_type,
                                           @Query("fields") String field,
                                           @Query("key") String api_key,
                                           @Query("q") String query_string);
}
