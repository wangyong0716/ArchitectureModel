package com.archi.database.common;

import android.database.Cursor;

import com.archi.database.DbManager;
import com.archi.database.info.IInfo;
import com.archi.database.storage.TableStorage;
import com.archi.database.utils.IOStreamUtils;

import java.util.LinkedList;
import java.util.List;

public class CommonStorage extends TableStorage {

    @Override
    public String getName() {
        return "common";
    }

    @Override
    public List<IInfo> readDb(String selection) {
        List<IInfo> infos = new LinkedList<IInfo>();
        Cursor cursor = null;
        try {
            cursor = DbManager.getInstance().getDbConfig().appContext.getContentResolver()
                    .query(getTableUri(), null, selection, null, null);
            if (null == cursor || !cursor.moveToFirst()) {
                IOStreamUtils.closeQuietly(cursor);
                return infos;
            }
            int indexText = cursor.getColumnIndex(CommonInfo.DBKey.TEXT);
            do {
                CommonInfo commonInfo = new CommonInfo();
                commonInfo.text = cursor.getString(indexText);
                infos.add(commonInfo);
            } while (cursor.moveToNext());
        } catch (Exception e) {
        } finally {
            IOStreamUtils.closeQuietly(cursor);
        }
        return infos;
    }
}
