package com.solariswu.gdrive.models;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class ShroudedData {

    @SerializedName("classification")
    public abstract String classification();

    public static ShroudedData create(String classification) {
        return builder()
                .classification(classification)
                .build();
    }

    public static Builder builder() {
        return new AutoValue_ShroudedData.Builder();
    }


    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder classification(String Classification);

        public abstract ShroudedData build();
    }

    public static TypeAdapter<ShroudedData> typeAdapter(Gson gson) {
        return new AutoValue_ShroudedData.GsonTypeAdapter(gson);
    }

}
