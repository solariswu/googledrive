package com.gapview.nume2.models;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class ShroudedData {

    @SerializedName("classification")
    public abstract List<String> classification();

    @SerializedName("tag_classfication")
    public abstract List<String> tag_classfication();


    public static ShroudedData create(List<String> classification, List<String>tag_classfication) {
        return builder()
                .classification(classification)
                .tag_classfication(tag_classfication)
                .build();
    }

    public static Builder builder() {
        return new AutoValue_ShroudedData.Builder();
    }


    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder classification(List<String> Classification);
        public abstract Builder tag_classfication(List<String> Tag_classfication);

        public abstract ShroudedData build();
    }

    public static TypeAdapter<ShroudedData> typeAdapter(Gson gson) {
        return new AutoValue_ShroudedData.GsonTypeAdapter(gson);
    }

}
