package com.gapview.nume2.models;


import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class File {

    @SerializedName("kind")
    public abstract String kind();

    @SerializedName("id")
    public abstract String id();

    @SerializedName("name")
    public abstract String name();

    @SerializedName("mimeType")
    public abstract String mimeType();

    public static Builder builder() {
        return new AutoValue_File.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder name(String name);
        public abstract Builder mimeType(String mimeType);

        public abstract Builder kind(String kind);

        public abstract Builder id(String id);

        public abstract File build();
    }

    public static TypeAdapter<File> typeAdapter(Gson gson) {
        return new AutoValue_File.GsonTypeAdapter(gson);
    }

}
