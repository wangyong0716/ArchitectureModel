package com.archi.database.storage;

import android.content.ContentValues;
import android.util.Log;

import com.archi.database.info.BaseInfo;
import com.archi.database.info.IInfo;
import com.archi.database.StorageManager;

import java.util.List;

public abstract class TableStorage implements IStorage {
    private static final String TAG = "TableStorage";

    @Override
    public boolean save(IInfo info) {
        ContentValues values = info.toContentValues();
        if (!values.containsKey(BaseInfo.KEY_TIME_RECORD)) {
            values.put(BaseInfo.KEY_TIME_RECORD, System.currentTimeMillis());
        }
        try{
            return StorageManager.getInstance().insert(getTableName(), values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean save(List<IInfo> infoList) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean clean() {
        return false;
    }

    @Override
    public boolean cleanByCount(int count) {
        return false;
    }

    @Override
    public boolean update(Integer id, ContentValues cvs) {
        return false;
    }

    @Override
    public IInfo getInfo(Integer key) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("select * from ").append(getName()).append(" where ");
        stringBuffer.append(BaseInfo.KEY_ID_RECORD).append("=");
        stringBuffer.append(key);
        String sql = stringBuffer.toString();
        List<IInfo> infoList = readDb(sql);
        return (null == infoList || infoList.isEmpty()) ? null : infoList.get(0);
    }

    @Override
    public List<IInfo> getAll() {
        Log.i(TAG, "getAll");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("select * from ").append(getName());
        String sql = stringBuffer.toString();
        return readDb(sql);
    }

    @Override
    public List<IInfo> getData(int index, int count) {
        // 读取flag为0的数据
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("select * from ").append(getName()).append(" order by id asc");
//        stringBuffer.append(BaseInfo.KEY_FLAG).append("=0");
        stringBuffer.append(" limit ").append(count).append(" offset ").append(index);
        String sql = stringBuffer.toString();
        return readDb(sql);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getTableName() {
        return null;
    }

    /**
     * 目前按照selection传入sql语句的方法来处理
     *
     * @param selection 待执行的sql语句
     * @return
     */
    public abstract List<IInfo> readDb(String selection);
}
