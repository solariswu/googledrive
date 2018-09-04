package com.solariswu.gdrive.services;

import android.support.annotation.NonNull;
import android.widget.Toast;


import com.solariswu.gdrive.models.ShroudedData;
import com.solariswu.gdrive.models.YoutubeData;
import com.solariswu.gdrive.ui.GdriveView;
import com.solariswu.gdrive.utils.Log;
import com.solariswu.gdrive.utils.GdriveConstant;


import org.json.JSONException;
import org.json.JSONObject;

import javax.annotation.Nullable;

import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.solariswu.gdrive.utils.GdriveConstant.GDRIVEPRESENTER_LOG;

/**
 * Created by solariswu on 16/10/5.
 *
 */

public class GdrivePresenter {

    //Retrofit Service variables
    private RetrofitService mYoutubeService;
    private RetrofitService mShroudedDataService;

    @Nullable
    private GdriveView mView;

    public void onCreate(@NonNull GdriveView gdriveViewView) {
        mView = gdriveViewView;

        // call View's implemented methods
        mView.initViews();

        // prepare the retrofit services
        Retrofit youtubeRetrofit = RetrofitManager.getInstance()
                .buildYoutubeRetrofit(GdriveConstant.YOUTUBEDATA_URL);

        mYoutubeService = youtubeRetrofit.create(RetrofitService.class);

        Retrofit shroudedDataRetrofit = RetrofitManager.getInstance()
                .buildShroudedDataRetrofit(GdriveConstant.SHROUDEDDATA_URL);

        mShroudedDataService = shroudedDataRetrofit.create(RetrofitService.class);
    }


    public void postShroudedData (String query) {
        Log.i(GDRIVEPRESENTER_LOG, "PostShroudedData:"+ query);

        // prepare call in Retrofit 2.0
        try {


            JSONObject paramObject = new JSONObject(query);

//            Log.d("My App", obj.toString());
//
//            JSONObject paramObject = new JSONObject();
//            paramObject.put("text", "I like beer");

            mShroudedDataService.postShroudedData(
                    paramObject.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ShroudedData>() {
                        @Override
                        public void onCompleted() {
                            Log.i (GDRIVEPRESENTER_LOG, "Post ShroudedData complete.");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i (GDRIVEPRESENTER_LOG, "Post ShroudedData meets error: "+e.getMessage());
                        }

                        @Override
                        public void onNext(ShroudedData shroudedData) {
                            if (null != mView) {
                                mView.updateWithShroudedData (shroudedData);
                            }
                        }
                    });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void fetchYoutubeData (String query) {
        Log.i(GDRIVEPRESENTER_LOG, "FetchYoutubeData:"+ query);

        mYoutubeService.getYoutubeData(GdriveConstant.YOUTUBEDATA_TYPE,
                GdriveConstant.YOUTUBEDATA_PART,
                "items/id",
                GdriveConstant.GCP_API_KEY,
                query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<YoutubeData>() {
                    @Override
                    public void onCompleted() {
                        Log.i (GDRIVEPRESENTER_LOG, "Search Youtube complete.");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i (GDRIVEPRESENTER_LOG, "Search Youtube meets error: "+e.getMessage());
                    }

                    @Override
                    public void onNext(YoutubeData youtubeData) {
                        if (null != mView) {
                            mView.updateWithYoutubeData (youtubeData);
                        }
                    }
                });
    }

}
