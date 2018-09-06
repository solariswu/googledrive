package com.solariswu.gdrive.utils;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

/**
 * Created by solariswu on September 5 2018.
 *
 * This factory is used to parse the data between Java class and Json format.
 *
 */


@GsonTypeAdapterFactory
public abstract class MyGsonTypeAdapterFactory implements TypeAdapterFactory {
    public static TypeAdapterFactory create() {
        return new AutoValueGson_MyGsonTypeAdapterFactory();
    }
}
