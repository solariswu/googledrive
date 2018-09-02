package com.solariswu.gdrive.services;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.solariswu.gdrive.utils.Log;
import com.solariswu.gdrive.utils.GdriveConstant;
import com.solariswu.gdrive.utils.MyGsonTypeAdapterFactory;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.IOException;

import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by yungang on 16/8/15.
 *
 */
public class RetrofitManager {
    private Retrofit mShroudedDataRetrofit;
    private Retrofit mYoutubeRetrofit;
    private OkHttpClient httpClient;
    private static RetrofitManager uniqueInstance = new RetrofitManager();

    private RetrofitManager() {
        // setting log level
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // add logging as last interceptor
        httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Log.i(GdriveConstant.YOUTUBESERVICE_LOG, "request :" + request.url().toString());
                        return chain.proceed(request);
                    }
                })
                .addInterceptor(logging)
                .build();
    }

    public static RetrofitManager getInstance() {
        return uniqueInstance;
    }

    public Retrofit buildShroudedDataRetrofit(String SERVICE_API_BASE_URL) {
        if (null == mShroudedDataRetrofit) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(MyGsonTypeAdapterFactory.create())
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();

            mShroudedDataRetrofit = new Retrofit.Builder()
                    .baseUrl(SERVICE_API_BASE_URL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return mShroudedDataRetrofit;
    }

    public Retrofit buildYoutubeRetrofit(String SERVICE_API_BASE_URL) {
        if (null == mYoutubeRetrofit) {

            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(MyGsonTypeAdapterFactory.create())
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();

            mYoutubeRetrofit = new Retrofit.Builder()
                    .baseUrl(SERVICE_API_BASE_URL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return mYoutubeRetrofit;
    }
}
