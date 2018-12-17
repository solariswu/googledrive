package com.gapview.nume2.services;


import com.gapview.nume2.models.GTokensData;
import com.gapview.nume2.models.GdriveData;
import com.gapview.nume2.models.ShroudedData;
import com.gapview.nume2.models.YoutubeData;

import java.util.HashMap;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
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

    @GET("/drive/v3/files")
    Observable<GdriveData> getGdriveData(@Header ("Authorization") String token,
                                         @Query("corpora") String corpora,
                                         @Query("q") String query_string);

    @POST("/oauth2/v4/token")
    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<GTokensData> getGoogleTokens(@Field("grant_type") String grantType,
                                            @Field("client_id") String cilentId,
                                            @Field("client_secret") String clientId,
                                            @Field("redirect_uri") String redirect_uri,
                                            @Field("code") String authCode);

}
