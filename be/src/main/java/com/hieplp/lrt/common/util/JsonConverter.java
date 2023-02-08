package com.hieplp.lrt.common.util;

import com.google.gson.*;

import java.util.Arrays;
import java.util.List;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 09/11/2022
 * Time: 11:04
 */
public class JsonConverter {
    private static Gson gson = new GsonBuilder()
            .disableHtmlEscaping()
            .setDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
            .create();

    private JsonConverter() {
        throw new IllegalStateException("Utility class: JsonConverter");
    }

    public static Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .disableHtmlEscaping()
                    .create();
        }
        return gson;
    }

    public static JsonElement toJsonElement(Object object) {
        return getGson().toJsonTree(object);
    }

    public static JsonElement toJsonElement(String object) {
        return JsonParser.parseString(object);
    }

    public static JsonObject toJsonObject(String json) {
        return JsonParser.parseString(json).getAsJsonObject();
    }

    public static JsonObject toJsonObject(Object object) {
        return JsonParser.parseString(getGson().toJson(object)).getAsJsonObject();
    }

    public static String toJson(Object object) {
        return getGson().toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return getGson().fromJson(json, clazz);
    }

    public static <T> T fromJson(JsonObject jsonObject, Class<T> clazz) {
        return getGson().fromJson(jsonObject, clazz);
    }

    public static <T> List<T> fromJsonToList(String json, Class<T[]> clazz) {
        return Arrays.asList(new Gson().fromJson(json, clazz));
    }
}
