package com.archi.database.storage;

import android.content.ContentValues;
import android.net.Uri;

import com.archi.database.info.IInfo;

import java.util.List;

public interface IStorage {

    boolean save(IInfo info);

    boolean save(List<IInfo> infoList);

    boolean delete(int id);

    boolean clean();

    boolean cleanByCount(int count);

    boolean update(Integer id, ContentValues cvs);

    IInfo getInfo(Integer id);

    List<IInfo> getAll();

    List<IInfo> getData(int index, int count);

    String getName();
}
