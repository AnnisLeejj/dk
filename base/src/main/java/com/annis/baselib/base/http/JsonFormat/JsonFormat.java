package com.annis.baselib.base.http.JsonFormat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

public class JsonFormat {
    private static Gson gson = null;

    public static Gson getGson() {
        if (gson == null) {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Date.class, new DateDeserializer());
            builder.registerTypeAdapter(Double.class, new DoubleTypeAdapter());
            builder.registerTypeAdapter(Long.class, new LongTypeAdapter());
            builder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory<String>());
            gson = builder.create();
        }
        return gson;
    }

    public static String toJson(Object o) {
        return getGson().toJson(o);
    }

    public static <T> T toObj(String s, Class<T> jsonCls) {
        return getGson().fromJson(s, jsonCls);
    }
}
