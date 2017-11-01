package com.huangwenbin.demoapi.common;

import com.google.gson.Gson;

public class JsonUtils {
    public static String toJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static <T> T toObject(String str, Class<T> classOfT) {
        Gson gson = new Gson();
        return gson.fromJson(str, classOfT);
    }
}
