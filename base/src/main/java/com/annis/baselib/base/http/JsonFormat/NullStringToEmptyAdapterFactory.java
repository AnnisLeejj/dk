package com.annis.baselib.base.http.JsonFormat;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

/**
 * @author Aswords
 * @Date 09/12/2016
 * @since v0.1
 */
public class NullStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {

    @SuppressWarnings({"unchecked", "hiding"})
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<T> rawType = (Class<T>) type.getRawType();
        if (rawType != String.class) {
            return null;
        }
        return (TypeAdapter<T>) new StringNullAdapter();
    }
}