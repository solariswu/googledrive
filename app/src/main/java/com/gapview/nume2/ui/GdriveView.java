package com.gapview.nume2.ui;

import com.gapview.nume2.models.ShroudedData;
import com.gapview.nume2.models.YoutubeData;

/**
 * Created by solariswu on September 5 2018.
 *
 */

public interface GdriveView {

    void initViews ();

    void updateWithYoutubeData (YoutubeData youtubeData);

    void updateWithShroudedData (ShroudedData shroudedData);

}
