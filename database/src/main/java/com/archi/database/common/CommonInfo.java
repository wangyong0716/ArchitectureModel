package com.archi.database.common;

import android.content.ContentValues;

import com.archi.database.info.BaseInfo;

import org.json.JSONException;
import org.json.JSONObject;

public class CommonInfo extends BaseInfo {

    String text;

    public static class DBKey {
        public static final String TEXT = "txt";
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject ori = super.toJson()
                .put(DBKey.TEXT, text);
        return ori;
    }

    @Override
    public void parserJson(JSONObject json) throws JSONException {
        this.text = json.getString(DBKey.TEXT);
    }

    @Override
    public void parserJsonStr(String json) throws JSONException {
        parserJson(new JSONObject(json));
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        try {
            values.put(DBKey.TEXT, text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }
}
