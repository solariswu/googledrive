package com.solariswu.gdrive.ui;

import com.solariswu.gdrive.models.ShroudedData;
import com.solariswu.gdrive.models.YoutubeData;

/**
 * Created by solariswu on 16/10/5.
 *
 */

public interface GdriveView {

    void initViews ();

    void updateWithYoutubeData (YoutubeData youtubeData);

    void updateWithShroudedData (ShroudedData shroudedData);

}
