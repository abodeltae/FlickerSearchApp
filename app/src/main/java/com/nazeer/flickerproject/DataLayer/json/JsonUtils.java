package com.nazeer.flickerproject.DataLayer.json;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class JsonUtils {

    static String getString(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.w("safe json parsing ", "safely handled failed parsing json string for key "+ key);
            return "";
        }
    }

    static int getInt(JSONObject jsonObject, String key) {

        try {
            return jsonObject.getInt(key);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.w("safe json parsing ", "safely handled failed  parsing json int for key "+ key);
            return 0;

        }

    }

    static double getDouble(JSONObject jsonObject, String key) {

        try {
            return jsonObject.getDouble(key);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.w("safe json parsing ", "safely handled failed  parsing json double for key "+ key);
            return 0;

        }


    }

    static JSONArray getJsonArray(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getJSONArray(key);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.w("safe json parsing ", "safely handled failed  parsing json array for key "+ key);
            return new JSONArray();
        }
    }
}
