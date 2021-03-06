package com.gapview.nume2.ui;


import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.gapview.nume2.R;
import com.gapview.nume2.utils.GdriveConstant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by solariswu on September 5 2018.
 *
 * Modified from Google Gdrive Fragment example.
 *
 */

public class FragmentYoutubeActivity extends YouTubeFailureRecoveryActivity {

    private static final String EXTRA_YOUTUBE_VIDEOID =
            "com.solariswu.gdrive.ui.EXTRA_YOUTUBE_VIDEOID";

    private String mVideoId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragments_youtube);

        mVideoId = getIntent().getStringExtra(EXTRA_YOUTUBE_VIDEOID);

        YouTubePlayerFragment youTubePlayerFragment =
                (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
        youTubePlayerFragment.initialize(GdriveConstant.GCP_API_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo(mVideoId);
        }
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
    }

    public static Intent getStartIntent (Context context, String videoId) {
        Intent intent = new Intent(context, FragmentYoutubeActivity.class);
        intent.putExtra(EXTRA_YOUTUBE_VIDEOID, videoId);
        return intent;
    }

}
