package com.archi.database.info;

import android.content.ContentValues;

import org.json.JSONException;
import org.json.JSONObject;

public interface IInfo {
    int getId();

    JSONObject toJson() throws JSONException;

    void parserJsonStr(String json) throws JSONException;

    void parserJson(JSONObject jsonObject) throws JSONException;

    ContentValues toContentValues();
}
