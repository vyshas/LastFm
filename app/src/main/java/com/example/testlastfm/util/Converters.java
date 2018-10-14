package com.example.testlastfm.util;

import android.arch.persistence.room.TypeConverter;

import com.example.testlastfm.model.Image;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class Converters {


    @TypeConverter
    public static List<String> fromString(String value) {
        Type listType = new TypeToken<List<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }


    @TypeConverter
    public static String fromList(List<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static List<Image> stringToImageList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Image>>() {}.getType();

        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String ImageListToString(List<Image> someObjects) {
        return new Gson().toJson(someObjects);
    }

    @TypeConverter
    public static List<Long> stringToLongList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Long>>() {}.getType();

        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String longListToString(List<Long> longs) {
        return new Gson().toJson(longs);
    }


}
