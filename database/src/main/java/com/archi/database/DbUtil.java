package com.archi.database;

import android.net.Uri;
import android.text.TextUtils;

public class DbUtil {

    /**
     * 获取各任务table的uri
     *
     * @param pkgName   包名
     * @param tableName table名称
     * @return uri
     */
    public static Uri getTableUri(String pkgName, String tableName) {
        if (TextUtils.isEmpty(pkgName) || TextUtils.isEmpty(tableName)) {
            return null;
        }
        String uriStr = TextUtils.concat(
                StorageConfig.CONTENT_PATH_PREFIX,
                getAuthority(pkgName),
                "/",
                tableName).toString();
        return Uri.parse(uriStr);
    }

    public static String getAuthority(String pkgName) {
        if (TextUtils.isEmpty(pkgName)) {
            return "";
        }
        return TextUtils.concat(pkgName, ".", StorageConfig.AUTHORITY_SUFFIX).toString();
    }
}
