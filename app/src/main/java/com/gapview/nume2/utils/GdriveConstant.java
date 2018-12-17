package com.gapview.nume2.utils;

/**
 * Created by yungang wu on September 5 2018.
 *
 */

public class GdriveConstant {
    //Log Identification String
    public static final String GDRIVEPRESENTER_LOG = "youtube_presenter";
    public static final String YOUTUBESERVICE_LOG   = "youtube_service";

    //Youtube Data API parameters
    public static final String YOUTUBEDATA_TYPE     = "video";
    public static final String YOUTUBEDATA_PART     = "snippet";

    //Google G cloud API KEY, url and data notification ID
    public static final String YOUTUBEDATA_URL      = "https://www.googleapis.com/";
    public static final String GCP_API_KEY          = "AIzaSyDlRIQN7yqxRS0xcjRndXuEgYBH9FzRX3A";
    public static final String SERVER_CLIENT_ID     = "1036598661932-v0806fnl1fj5jv9nb0cq74cvnkiu1flo.apps.googleusercontent.com";
    public static final String CLIENT_SECRET         = "iK06EeV8OcveRvNoqU6fsLk3";

    //Predict server url, json data key
    public static final String SHROUDEDDATA_URL     = "https://shrouded-basin-44060.herokuapp.com/";
    public static final String SHROUDEDATA_JSON_KEY = "text";
}
