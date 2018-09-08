package com.gapview.nume2.models;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class IdBean {

    @SerializedName("kind")
    public abstract String kind();

    @SerializedName("videoId")
    public abstract String videoId();

    public static TypeAdapter<IdBean> typeAdapter(Gson gson) {
        return new AutoValue_IdBean.GsonTypeAdapter(gson);
    }

}
