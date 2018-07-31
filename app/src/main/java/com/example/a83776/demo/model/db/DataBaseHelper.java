package com.example.a83776.demo.model.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.a83776.demo.model.bean.User;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


/**
 * description:
 * author: GaoJie
 * created at: 2018/6/21 15:21
 */
public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    private static DataBaseHelper sInstance;

    private static final String DB_NAME = "demo.db";
    private static final int DB_VERSION = 1;
    private Map<String, Dao> daos = new HashMap<>();

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

    }

    /**
     * 单例获取该Helper
     *
     * @param context
     * @return
     */
    public static synchronized DataBaseHelper getHelper(Context context) {
        context = context.getApplicationContext();
        if (sInstance == null) {
            synchronized (DataBaseHelper.class) {
                if (sInstance == null) {
                    sInstance = new DataBaseHelper(context);
                }
            }
        }
        return sInstance;
    }

    /**
     * 获取dao
     *
     * @param clazz
     * @return
     * @throws SQLException
     */
    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();
        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }

    /**
     * 释放资源
     */
    @Override
    public void close() {
        super.close();
        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }
}
