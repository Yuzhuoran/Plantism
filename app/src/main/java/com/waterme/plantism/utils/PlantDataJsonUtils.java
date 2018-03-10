package com.waterme.plantism.utils;

import android.content.ContentValues;
import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Utils to conver json to content values
 * Created by zhuoran on 3/9/18.
 */

public class PlantDataJsonUtils {

    public static ContentValues[] getPlantContentValuesFromJson(Context context, String PlantJsonStr)
        throws JSONException {

        JSONObject plantJson = new JSONObject(PlantJsonStr);
        ContentValues[] contentValues = new ContentValues[5];
        return contentValues;
    }
}
