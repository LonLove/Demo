package com.example.a83776.demo.model.db;

/**
 * description: 数据库帮助类
 * author: GaoJie
 * created at: 2018/6/21 15:57
 */

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.PreparedUpdate;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableUtils;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 数据库CRUD操作的Dao，子类继承实现抽象方法，也提供一个简单的泛型实现类
 */
public abstract class BaseDao<T, Integer> {

    private Context mContext;
    private DataBaseHelper mDataBaseHelper;

    //上下文
    public BaseDao(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context can't be null!");//如果为空，抛出非法参数异常
        }
        mContext = context.getApplicationContext();//避免内存泄漏，使用getApplicationContext();
        mDataBaseHelper = DataBaseHelper.getHelper(mContext);
    }

    /**
     * 抽象方法。重写提供Dao，在BaseDaoImpl里提供了简单的泛型实现，传递实体类Class即可
     *
     * @return Dao类
     * @throws SQLException
     */
    public abstract Dao<T, Integer> getDao() throws SQLException;

    /**
     * 增，带事务操作
     *
     * @param t 泛型实体类
     * @return
     * @throws SQLException
     */
    public int save(T t) throws SQLException {
        Dao<T, Integer> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            int save = dao.create(t);
            dao.commit(databaseConnection);
            return save;
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return 0;
    }

    /**
     * 增或更新，带事务操作
     *
     * @param t 泛型实体类
     * @return 影响的行数
     * @throws SQLException
     */
    public Dao.CreateOrUpdateStatus saveOrUpdate(T t) throws SQLException {
        Dao<T, Integer> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            Dao.CreateOrUpdateStatus orUpdate = dao.createOrUpdate(t);
            dao.commit(databaseConnection);
            return orUpdate;
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return null;
    }

    /**
     * 增，带事务操作
     *
     * @param t 泛型实体类集合
     * @return 影响的行数
     * @throws SQLException
     */
    public int save(List<T> t) throws SQLException {
        Dao<T, Integer> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            for (T item : t) {
                dao.create(item);
            }
            dao.commit(databaseConnection);
            return t.size();
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return 0;
    }

    /**
     * 删，带事务操作
     *
     * @param t 泛型实体类
     * @return 影响的行数
     * @throws SQLException
     */
    public int delete(T t) throws SQLException {
        Dao<T, Integer> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            int delete = dao.delete(t);
            dao.commit(databaseConnection);
            return delete;
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return 0;
    }

    /**
     * 删，带事务操作
     *
     * @param t 泛型实体类集合
     * @return 影响的行数
     * @throws SQLException
     */
    public int delete(List<T> t) throws SQLException {
        Dao<T, Integer> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            int delete = dao.delete(t);
            dao.commit(databaseConnection);
            return delete;
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return 0;
    }

    /**
     * 删，带事务操作
     *
     * @param id id值
     * @return 影响的行数
     * @throws SQLException
     */
    public int deleteById(Integer id) throws SQLException {
        Dao<T, Integer> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            int delete = dao.deleteById(id);
            dao.commit(databaseConnection);
            return delete;
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return 0;
    }

    /**
     * 删，带事务操作
     *
     * @param ids id集合
     * @return 影响的行数
     * @throws SQLException
     */
    public int deleteByIds(List<Integer> ids) throws SQLException {
        Dao<T, Integer> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            int delete = dao.deleteIds(ids);
            dao.commit(databaseConnection);
            return delete;
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return 0;
    }

    /**
     * 删，带事务操作
     *
     * @param preparedDelete preparedDelete类
     * @return 影响的行数
     * @throws SQLException
     */
    public int delete(PreparedDelete<T> preparedDelete) throws SQLException {
        Dao<T, Integer> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            int delete = dao.delete(preparedDelete);
            dao.commit(databaseConnection);
            return delete;
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return 0;
    }

    /**
     * 改，带事务操作
     *
     * @param t 泛型实体类
     * @return 影响的行数
     * @throws SQLException
     */
    public int update(T t) throws SQLException {
        Dao<T, Integer> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            int update = dao.update(t);
            dao.commit(databaseConnection);
            return update;
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return 0;
    }

    /**
     * 改，带事务操作
     *
     * @param preparedUpdate 泛型实体类
     * @return 影响的行数
     * @throws SQLException
     */
    public int update(PreparedUpdate<T> preparedUpdate) throws SQLException {
        Dao<T, Integer> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            int update = dao.update(preparedUpdate);
            dao.commit(databaseConnection);
            return update;
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return 0;
    }

    /**
     * 查，带事务操作
     *
     * @return 查询结果集合
     * @throws SQLException
     */
    public List<T> queryAll() throws SQLException {
        Dao<T, Integer> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            List<T> query = dao.queryForAll();
            dao.commit(databaseConnection);
            return query;
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return null;
    }

    /**
     * 查，带事务操作
     *
     * @param preparedQuery preparedQuery对象
     * @return 查询结果集合
     * @throws SQLException
     */
    public List<T> query(PreparedQuery preparedQuery) throws SQLException {
        Dao<T, Integer> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            List<T> query = dao.query(preparedQuery);
            dao.commit(databaseConnection);
            return query;
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return null;
    }

    /**
     * 查，带事务操作
     *
     * @param columnName  列名
     * @param columnValue 列名对应的值
     * @return 查询结果集合
     * @throws SQLException
     */
    public List<T> query(String columnName, String columnValue) throws SQLException {
        QueryBuilder<T, Integer> queryBuilder = getDao().queryBuilder();
        if (columnValue == null) {
            queryBuilder.where().isNull(columnName);
            // queryBuilder.where().eq(columnName,columnValue)
        } else {
            queryBuilder.where().isNotNull(columnName);
        }
        PreparedQuery<T> preparedQuery = queryBuilder.prepare();
        Dao<T, Integer> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            List<T> query = dao.query(preparedQuery);
            //also can use dao.queryForEq(columnName,columnValue);
            dao.commit(databaseConnection);
            return query;
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return null;
    }

    /**
     * 查，带事务操作
     *
     * @param columnNames  列名数组
     * @param columnValues 列名对应的值数组
     * @return 查询结果集合
     * @throws SQLException
     */
    public List<T> query(String[] columnNames, String[] columnValues) throws SQLException {
        if (columnNames.length != columnValues.length) {
            throw new InvalidParameterException("params size is not equal");
        }
        QueryBuilder<T, Integer> queryBuilder = getDao().queryBuilder();
        Where<T, Integer> wheres = queryBuilder.where();
        for (int i = 0; i < columnNames.length; i++) {
            if (i == 0) {
                wheres.eq(columnNames[i], columnValues[i]);
            } else {
                wheres.and().eq(columnNames[i], columnValues[i]);
            }
        }
        PreparedQuery<T> preparedQuery = queryBuilder.prepare();
        Dao<T, Integer> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            List<T> query = dao.query(preparedQuery);
            //also can use dao.queryForEq(columnName,columnValue);
            dao.commit(databaseConnection);
            return query;
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return null;
    }

    /**
     * 查，带事务操作
     *
     * @param map 列与值组成的map
     * @return 查询结果集合
     * @throws SQLException
     */
    public List<T> query(Map<String, Object> map) throws SQLException {
        QueryBuilder<T, Integer> queryBuilder = getDao().queryBuilder();
        if (!map.isEmpty()) {
            Where<T, Integer> wheres = queryBuilder.where();
            Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
            String key = null;
            Object value = null;
            for (int i = 0; iterator.hasNext(); i++) {
                Map.Entry<String, Object> next = iterator.next();
                key = next.getKey();
                value = next.getValue();
                if (i == 0) {
                    wheres.eq(key, value);
                } else {
                    wheres.and().eq(key, value);
                }
            }
        }
        PreparedQuery<T> preparedQuery = queryBuilder.prepare();
        Dao<T, Integer> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            List<T> query = dao.query(preparedQuery);
            //also can use dao.queryForEq(columnName,columnValue);
            dao.commit(databaseConnection);
            return query;
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return null;
    }

    /**
     * 查，带事务操作
     * @param id id值
     * @return 查询结果集合
     * @throws SQLException
     */
    public T queryById(Integer id) throws SQLException {
        Dao<T, Integer> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            T t = dao.queryForId(id);
            dao.commit(databaseConnection);
            return t;
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return null;
    }

    /**
     * 判断表是否存在
     * @return 表是否存在
     * @throws SQLException
     */
    public boolean isTableExists() throws SQLException {
        return getDao().isTableExists();
    }
    /**
     * 清空表数据
     *
     * @param table 数据表
     * @return
     * @throws SQLException
     */
    public int clearTable(Class<T> table) throws SQLException, IOException {
        Dao<T, Integer> dao = getDao();
        ConnectionSource connectionSource = dao.getConnectionSource();
        try {
            return TableUtils.createTable(connectionSource, table);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionSource.close();
        }
        return java.lang.Integer.MAX_VALUE;
    }
}
