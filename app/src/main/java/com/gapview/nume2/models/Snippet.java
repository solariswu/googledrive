package com.gapview.nume2.models;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Snippet {

    @SerializedName("description")
    public abstract String description();

    @SerializedName("title")
    public abstract String title();

    public static TypeAdapter<Snippet> typeAdapter(Gson gson) {
        return new AutoValue_Snippet.GsonTypeAdapter(gson);
    }

}
