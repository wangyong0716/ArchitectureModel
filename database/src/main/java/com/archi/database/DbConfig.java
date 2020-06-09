package com.archi.database;

import android.content.Context;

public class DbConfig {

    public Context appContext;

    public static class DBConfigBuilder {
        private DbConfig dbConfig = new DbConfig();


        public DBConfigBuilder setAppContext(Context appContext) {
            this.dbConfig.appContext = appContext;
            return this;
        }

        public DbConfig build() {
            return dbConfig;
        }
    }
}
