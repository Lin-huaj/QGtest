package com.dao;
import com.annotation.Table;
import com.annotation.Column;
import com.annotation.Id;
import com.utils.ConnectionPool;

import java.lang.reflect.Field;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDao<T> {
    protected final ConnectionPool connectionPool;
    private final Class<T> entityClass;
    private ThreadLocal<Connection> transactionConn = new ThreadLocal<>();

    public BaseDao(ConnectionPool connectionPool, Class<T> entityClass) {
        this.connectionPool = connectionPool;
        this.entityClass = entityClass;
    }

    // 插入数据方法，返回受影响行数
    public int insert(T entity) throws SQLException, IllegalAccessException {
        // 获取实体类对应的数据库表名（通过@Table注解）
        String tableName = getTableName();
        // 获取实体类中带有@Column注解的字段列表
        List<Field> fields = getEntityFields();

        try {
            // 构建INSERT语句的列名部分
            StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + " (");
            // 构建VALUES部分的占位符
            StringBuilder values = new StringBuilder(" VALUES (");
            // 存储PreparedStatement的参数值
            List<Object> params = new ArrayList<>();

            // 遍历所有带有@Column注解的字段
            for (Field field : fields) {
                // 允许访问私有字段（可能抛出SecurityException）
                field.setAccessible(true);
                // 获取字段上的@Column注解
                Column column = field.getAnnotation(Column.class);
                if (column != null) {
                    // 拼接列名（例如："name,"）
                    sql.append(column.value()).append(",");
                    // 添加占位符（例如："?,"）
                    values.append("?,");
                    // 获取字段值（可能抛出IllegalAccessException）
                    params.add(field.get(entity));
                }
            }
            // 修正列名部分的结尾：删除最后一个逗号，添加右括号
            sql.deleteCharAt(sql.length() - 1).append(")");
            // 修正VALUES部分的结尾：删除最后一个逗号，添加右括号
            values.deleteCharAt(values.length() - 1).append(")");
            // 合并完整的SQL语句（例如："INSERT INTO users (name,age) VALUES (?,?)"）
            sql.append(values);

            // 执行SQL并返回受影响行数
            return executeUpdate(sql.toString(), params);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.err.println("SQL Error:"+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // 更新数据
    public int update(T entity) throws SQLException, IllegalAccessException {
        String tableName = getTableName();
        List<Field> fields = getEntityFields();

        StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");
        List<Object> params = new ArrayList<>();
        Object idValue = null;

        for (Field field : fields) {
            field.setAccessible(true);
            Column column = field.getAnnotation(Column.class);
            Id idAnnotation = field.getAnnotation(Id.class);

            if (column != null && idAnnotation == null) {
                sql.append(column.value()).append("=?,");
                params.add(field.get(entity));
            } else if (idAnnotation != null) {
                idValue = field.get(entity);
            }
        }

        sql.deleteCharAt(sql.length() - 1);
        sql.append(" WHERE id=?");
        params.add(idValue);

        return executeUpdate(sql.toString(), params);
    }

    // 删除数据
    public int delete(Object id) throws SQLException {
        String tableName = getTableName();
        String sql = "DELETE FROM " + tableName + " WHERE id=?";  // 使用占位符
        List<Object> params = new ArrayList<>();
        params.add(id);
        return executeUpdate(sql, params);  // 传递参数列表
    }

    // 查询所有数据
    public List<T> findAll() throws SQLException {
        String tableName = getTableName();
        String sql = "SELECT * FROM " + tableName;
        return executeQuery(sql, new ArrayList<>());
    }

    // 根据ID查询
    public T findById(Object id) throws SQLException {
        String tableName = getTableName();
        String sql = "SELECT * FROM " + tableName + " WHERE id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        List<T> result = executeQuery(sql, params);
        return result.isEmpty() ? null : result.get(0);
    }

    // 执行更新操作
    protected int executeUpdate(String sql, List<Object> params) throws SQLException {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            return pstmt.executeUpdate();
        }
    }

    // 执行查询操作
    protected List<T> executeQuery(String sql, List<Object> params) throws SQLException {
        List<T> result = new ArrayList<>();

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    T entity = mapResultSetToEntity(rs);
                    result.add(entity);
                }
            }
        }
        return result;
    }

    // 结果集映射到实体
    private T mapResultSetToEntity(ResultSet rs) throws SQLException {
        try {
            T entity = entityClass.getDeclaredConstructor().newInstance();
            List<Field> fields = getEntityFields();


            for (Field field : fields) {
                field.setAccessible(true);
                Column column = field.getAnnotation(Column.class);
                if (column != null) {
                    Object value = rs.getObject(column.value());
                    if (value instanceof Timestamp && field.getType() == LocalDateTime.class) {
                        value = ((Timestamp) value).toLocalDateTime();
                    }
                    field.set(entity, value);
                }
            }

            return entity;
        } catch (Exception e) {
            throw new SQLException("Failed to map ResultSet to entity", e);
        }
    }

    // 获取实体类字段
    List<Field> getEntityFields() {
        List<Field> fields = new ArrayList<>();
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                fields.add(field);
            }
        }
        return fields;
    }

    // 获取表名
    String getTableName() {
        Table table = entityClass.getAnnotation(Table.class);
        return table != null ? table.value() : entityClass.getSimpleName().toLowerCase();
    }

    //增加事物支持
    public void beginTransaction() throws SQLException {
        Connection conn = connectionPool.getConnection();
        conn.setAutoCommit(false);
        transactionConn.set(conn);
    }

    public void commitTransaction() {
        Connection conn = transactionConn.get();
        if (conn != null) {
            try {
                conn.commit();
            } catch (SQLException e) {
                try {
                    conn.rollback();  // 提交失败时回滚
                } catch (SQLException ex) {
                    throw new RuntimeException("事务回滚失败", ex);
                }
                throw new RuntimeException("事务提交失败", e);
            } finally {
                try {
                    conn.setAutoCommit(true);  // 恢复自动提交
                } catch (SQLException ignored) {}
                connectionPool.releaseConnection(conn);
                transactionConn.remove();
            }
        }
    }
    public void rollbackTransaction() {
        Connection conn = transactionConn.get();
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                throw new RuntimeException("事务回滚失败", e);
            } finally {
                try { conn.setAutoCommit(true); } catch (SQLException ignored) {}
                connectionPool.releaseConnection(conn);
                transactionConn.remove();
            }
        }
    }

    private Connection getConnection() throws SQLException {
        return transactionConn.get() != null ?
                transactionConn.get() :
                connectionPool.getConnection();
    }

}