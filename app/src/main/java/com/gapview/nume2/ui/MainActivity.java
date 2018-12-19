package com.gapview.nume2.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gapview.nume2.R;
import com.gapview.nume2.models.ShroudedData;
import com.gapview.nume2.models.YoutubeData;
import com.gapview.nume2.services.GdrivePresenter;
import com.gapview.nume2.utils.WordCount;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by solariswu on September 5 2018.
 *
 * Main UI page of Google drive pick and search Youtube demo.
 *
 */

public class MainActivity extends AppCompatActivity implements GdriveView {

    private static final String EXTRA_IS_FROM_DGRIVE =
            "com.solariswu.gdrive.ui.EXTRA_FROM_DGRIVE";

    private static final String EXTRA_CONTENT_COUNT =
            "com.solariswu.gdrive.ui.EXTRA_CONTENT_COUNT";

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

        mButtonPick.setText(R.string.Next);
        mButtonPick.setClickable(true);

        if (getIntent().getBooleanExtra(EXTRA_IS_FROM_DGRIVE, false)) {
            ArrayList<String> gdriveContent = getIntent().getStringArrayListExtra(EXTRA_GDRIVE_CONTENT);
            Integer contentCount = getIntent().getIntExtra(EXTRA_CONTENT_COUNT,0);
            //Toast.makeText(this, "Retrieved file content: " + gdriveContent,
            //        Toast.LENGTH_LONG).show();
            if (0 < contentCount) {
                mGdrivePresenter.postShroudedData(contentCount, gdriveContent);
                mButtonPick.setText(R.string.Loading);
                mButtonPick.setClickable(false);
            }
            else {
                Toast.makeText(this, "No content!", Toast.LENGTH_LONG).show();

            }
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
        String query_string;

        String classification = shroudedData.classification().toString();
        classification = classification.substring(1, classification.length()-1);

        String tag = shroudedData.tag_classfication().toString();
        tag = tag.substring(1, tag.length()-1);

        query_string = WordCount.getHighestWord(classification);
        query_string += " " + WordCount.getHighestWord(tag);

        mGdrivePresenter.fetchYoutubeData(query_string);
        //Toast.makeText(this, "Got the server predict classification: " +
        //        shroudedData.classification(), Toast.LENGTH_LONG).show();


    }

    @Override
    public void updateWithYoutubeData (YoutubeData youtubeData) {

        if (null != youtubeData) {
            String videoId = youtubeData.items().get(0).id().videoId();
            String title1 = youtubeData.items().get(0).snippet().title();
            String videoId2 = youtubeData.items().get(1).id().videoId();
            String title2 = youtubeData.items().get(2).snippet().title();

            startActivity(YouTubeVideoListActivity.getStartIntent(this,
                    videoId, videoId2, title1, title2));

        }
        else {
            Toast.makeText(this, "No valid Youtube Video search result found!",
                    Toast.LENGTH_LONG).show();
        }

        mButtonPick.setText(R.string.Next);
        mButtonPick.setClickable(true);

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

    public static Intent getStartIntent (Context context, Integer contentCount, ArrayList<String> content) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA_IS_FROM_DGRIVE, true);
        intent.putExtra(EXTRA_CONTENT_COUNT, contentCount);
        intent.putExtra(EXTRA_GDRIVE_CONTENT, content);
        return intent;
    }
}
