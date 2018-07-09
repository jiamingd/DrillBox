package com.example.jyu.drillbox.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.InvocationTargetException;

public class EnumUtils {

    @Nullable
    public static String getSerializedName(@Nullable Enum enumObject) {
        if (enumObject == null) {
            return null;
        }
        try {
            SerializedName annotation = enumObject.getClass().getField(enumObject.name()).getAnnotation(SerializedName.class);
            return annotation != null ? annotation.value() : null;
        } catch (NoSuchFieldException e) {
            throw new AssertionError("should never ever happen since we are getting the field using this.name()");
        }
    }

    @Nullable
    public static <T extends Enum> T deserialize(@Nullable String name, @NonNull Class<T> klass) {
        if (name == null) {
            return null;
        }
        try {
            for (T instance : (T[])klass.getMethod("values").invoke(klass)) {
                if (TextUtils.equals(name, EnumUtils.getSerializedName(instance))) {
                    Log.i("","Deserialized " + name + " to " + instance);
                    return instance;
                }
            }
            Log.i("","Failed to deserilize " + name + " as instance of " + klass.getSimpleName());
        } catch (NoSuchMethodException|IllegalAccessException|InvocationTargetException e) {
            Log.e("Catastrophic failure to deserialize " + name + " as instance of " + klass.getSimpleName(), e.toString());
            throw new RuntimeException(e);
        }
        return null;
    }
}
