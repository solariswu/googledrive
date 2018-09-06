package com.solariswu.gdrive.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.solariswu.gdrive.R;
import com.solariswu.gdrive.models.ShroudedData;
import com.solariswu.gdrive.models.YoutubeData;
import com.solariswu.gdrive.services.GdrivePresenter;


import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;



public class MainActivity extends AppCompatActivity implements GdriveView {

    private static final String EXTRA_IS_FROM_DGRIVE =
            "com.solariswu.gdrive.ui.EXTRA_FROM_DGRIVE";

    private static final String EXTRA_GDRIVE_CONTENT =
            "com.solariswu.gdrive.ui.EXTRA_GDRIVE_CONTENT";

    //MVP presenter
    private GdrivePresenter mGdrivePresenter;

    //Butter knife variables
    Unbinder unbinder;

    //UI elements
    @BindView(R.id.tv_logo)
    TextView mTVGeoLocation;

    @BindView(R.id.btn_pick)
    Button mButtonPick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);

        // Bind presenter
        mGdrivePresenter = new GdrivePresenter();
        mGdrivePresenter.onCreate(this);

        if (getIntent().getBooleanExtra(EXTRA_IS_FROM_DGRIVE, false)) {
            String gdriveContent = getIntent().getStringExtra(EXTRA_GDRIVE_CONTENT);
            Toast.makeText(this, "Retrieved file content: " + gdriveContent,
                    Toast.LENGTH_LONG).show();
            mGdrivePresenter.postShroudedData(gdriveContent);
        }
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
        //mTVGeoLocation.setText(shroudedData.classification());
        mGdrivePresenter.fetchYoutubeData(shroudedData.classification());
        Toast.makeText(this, "Got the server predict classification: " +
                shroudedData.classification(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateWithYoutubeData (YoutubeData youtubeData) {
        //mTVGeoLocation.setText(youtubeData.items().get(0).id().videoId());
        if (null != youtubeData) {
            String videoId = youtubeData.items().get(0).id().videoId();
            startActivity(FragmentYoutubeActivity.getStartIntent(this,
                    videoId));
            Toast.makeText(this, "Youtube Search result - videoId: " + videoId,
                    Toast.LENGTH_LONG).show();
        }
        else Toast.makeText(this, "No valid Youtube Video search result found!",
                Toast.LENGTH_LONG).show();
    }

    @OnClick({R.id.btn_pick})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_pick:
                // pick a file and read its content from google drive
                Intent intent = new Intent(getBaseContext(), RetrieveContentsActivity.class);
                startActivity(intent);
                break;
        }

    }

    public static Intent getStartIntent (Context context, String content) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA_IS_FROM_DGRIVE, true);
        intent.putExtra(EXTRA_GDRIVE_CONTENT, content);
        return intent;
    }
}
