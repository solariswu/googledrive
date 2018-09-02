package com.solariswu.gdrive.models;


import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Item {


    /**
     * kind : youtube#searchResult
     * etag : XI7nbFXulYBIpL0ayR_gDh3eu1kcx96QgLLWZH_JxHSfGIB_8-HdN0
     * id : {"kind":"youtube#video","videoId":"aJOTlE1K90k"}
     * snippet : {"publishedAt":"2018-05-31T04:00:00.000Z","channelId":"UCN1hnUccO4FD5WfM7ithXaw","title":"Maroon 5 - Girls Like You ft. Cardi B","description":"Girls Like You is out now. http://smarturl.it/GLY For more, visit: https://www.facebook.com/maroon5 https://twitter.com/maroon5 ...","thumbnails":{"default":{"url":"https://i.ytimg.com/vi/aJOTlE1K90k/default.jpg","width":120,"height":90},"medium":{"url":"https://i.ytimg.com/vi/aJOTlE1K90k/mqdefault.jpg","width":320,"height":180},"high":{"url":"https://i.ytimg.com/vi/aJOTlE1K90k/hqdefault.jpg","width":480,"height":360}},"channelTitle":"Maroon5VEVO","liveBroadcastContent":"none"}
     */

    @SerializedName("id")
    public abstract IdBean id();

    public static Builder builder() {
        return new AutoValue_Item.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(IdBean id);

        public abstract Item build();
    }

    public static TypeAdapter<Item> typeAdapter(Gson gson) {
        return new AutoValue_Item.GsonTypeAdapter(gson);
    }

}
