package com.gapview.nume2.models;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class GTokensData {

    @SerializedName("expires_in")
    public abstract Integer expires_in();

    @SerializedName("access_token")
    public abstract String access_token();

    public static GTokensData create(Integer expires_in, String access_token) {
        return builder()
                .expires_in(expires_in)
                .access_token(access_token)
                .build();
    }

    public static Builder builder() {
        return new AutoValue_GTokensData.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder expires_in(Integer expires_in);

        public abstract Builder access_token(String access_token);

        public abstract GTokensData build();
    }

    public static TypeAdapter<GTokensData> typeAdapter(Gson gson) {
        return new AutoValue_GTokensData.GsonTypeAdapter(gson);
    }

}
