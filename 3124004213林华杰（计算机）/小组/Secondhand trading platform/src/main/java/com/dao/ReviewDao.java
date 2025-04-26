package com.dao;

import com.annotation.Column;
import com.pojo.Review;
import com.utils.ConnectionPool;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDao extends BaseDao<Review> {
    private static final int POSITIVE_SCORE_CHANGE = 5;
    private static final int NEGATIVE_SCORE_CHANGE = -5;

    public ReviewDao(ConnectionPool pool) {
        super(pool, Review.class);
    }

    public int addReview(Review review, int sellerId) throws SQLException {
        Connection conn = null;
        try {
            conn = super.connectionPool.getConnection(); // 使用BaseDao的connectionPool
            conn.setAutoCommit(false);

            // 1. 手动执行INSERT操作（替代BaseDao的insert方法）
            int rows = manualInsertReview(review, conn);

            if (rows > 0) {
                // 2. 更新卖家信用分
                int scoreChange = calculateScoreChange(review.getRating());
                String updateSql = "UPDATE users SET credit_score = credit_score + ? WHERE user_id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                    pstmt.setInt(1, scoreChange);
                    pstmt.setInt(2, sellerId);
                    pstmt.executeUpdate();
                }

                conn.commit();
            }
            return rows;
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw new SQLException("操作失败: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ignored) {}
            }
        }
    }

    /**
     * 手动实现INSERT逻辑（原BaseDao的insert方法功能）
     */
    private int manualInsertReview(Review review, Connection conn) throws SQLException {
        String tableName = getTableName(); // 假设BaseDao提供获取表名的方法
        List<Field> fields = getEntityFields(); // 假设BaseDao提供获取字段的方法

        StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + " (");
        StringBuilder values = new StringBuilder(" VALUES (");
        List<Object> params = new ArrayList<>();

        try {
            for (Field field : fields) {
                field.setAccessible(true);
                Column column = field.getAnnotation(Column.class);
                if (column != null) {
                    sql.append(column.value()).append(",");
                    values.append("?,");
                    params.add(field.get(review)); // 获取字段值
                }
            }
        } catch (IllegalAccessException e) {
            throw new SQLException("反射获取字段值失败: " + e.getMessage(), e);
        }

        sql.deleteCharAt(sql.length() - 1).append(")");
        values.deleteCharAt(values.length() - 1).append(")");
        sql.append(values);

        try (PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }
            return pstmt.executeUpdate();
        }
    }

    private int calculateScoreChange(int rating) {
        return rating >= 4 ? POSITIVE_SCORE_CHANGE : NEGATIVE_SCORE_CHANGE;
    }
}