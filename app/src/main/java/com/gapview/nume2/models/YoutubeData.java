package com.gapview.nume2.models;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@AutoValue
public abstract class YoutubeData {

    @SerializedName("items")
    public abstract List<Item> items();

    public static YoutubeData create(List<Item> items) {
        return builder()
                .items(items)
                .build();
    }

    public static Builder builder() {
        return new AutoValue_YoutubeData.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder items(List<Item> items);

        public abstract YoutubeData build();
    }

    public static TypeAdapter<YoutubeData> typeAdapter(Gson gson) {
        return new AutoValue_YoutubeData.GsonTypeAdapter(gson);
    }

}
