package com.telstra.exercise.util;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JsonUtils {

	public static <T> T fromJson(String json, TypeToken<T> token) {
		if (TextUtils.isEmpty(json)) {
			return null;
		}

        Gson gson = new Gson();
		return gson.fromJson(json, token.getType());
	}

	public static <T> T fromJson(String json, Class<T> clazz) {
		if (TextUtils.isEmpty(json)) {
			return null;
		}

        Gson gson = new Gson();
		return gson.fromJson(json, clazz);
	}
}
