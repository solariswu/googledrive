package com.solariswu.gdrive.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.solariswu.gdrive.R;
import com.solariswu.gdrive.models.ShroudedData;
import com.solariswu.gdrive.models.YoutubeData;
import com.solariswu.gdrive.services.GdrivePresenter;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;



public class MainActivity extends AppCompatActivity implements GdriveView {

    //MVP presenter
    private GdrivePresenter mGdrivePresenter;

    //Butter knife variables
    Unbinder unbinder;

    //UI elements
    @BindView(R.id.tv_location)
    TextView mTVGeoLocation;

    @BindView(R.id.btn_play)
    Button mButtonPlay;

    @BindView(R.id.btn_search)
    Button mButtonSearch;

    @BindView(R.id.btn_post)
    Button mButtonPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);

        // Bind presenter
        mGdrivePresenter = new GdrivePresenter();
        mGdrivePresenter.onCreate(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    @Override
    public void initViews() {

    }

    @Override
    public void updateWithShroudedData (ShroudedData shroudedData) {
        mTVGeoLocation.setText(shroudedData.classification());
    }

    @Override
    public void updateWithYoutubeData (YoutubeData youtubeData) {
        mTVGeoLocation.setText(youtubeData.items().get(0).id().videoId());
    }

    @OnClick({R.id.btn_play, R.id.btn_search, R.id.btn_post})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_play:
                startActivity(FragmentYoutubeActivity.getStartIntent(this));
                break;
            case R.id.btn_search:
                mGdrivePresenter.fetchYoutubeData("2CELO");
                break;
            case R.id.btn_post:
                mGdrivePresenter.PostShroudedData("{\"text\":\"I like beer\"}");
                break;
        }

    }
}
