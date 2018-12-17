package com.gapview.nume2.models;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class GdriveData {

    @SerializedName("files")
    public abstract List<File> files();

    public static GdriveData create(List<File> files) {
        return builder()
                .files(files)
                .build();
    }

    public static Builder builder() {
        return new AutoValue_GdriveData.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder files(List<File> files);

        public abstract GdriveData build();
    }

    public static TypeAdapter<GdriveData> typeAdapter(Gson gson) {
        return new AutoValue_GdriveData.GsonTypeAdapter(gson);
    }

}
