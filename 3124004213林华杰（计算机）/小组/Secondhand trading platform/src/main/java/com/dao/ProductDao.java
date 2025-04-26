package com.dao;

import com.pojo.Product;
import com.utils.ConnectionPool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProductDao extends BaseDao<Product>{
    public ProductDao(ConnectionPool pool) {
        super(pool, Product.class);
    }

    public List<Product> findBySeller(int sellerId) throws SQLException {
        String sql = "SELECT * FROM Product WHERE seller_id = ?";
        return executeQuery(sql, Collections.singletonList(sellerId));
    }

    public int updateStock(int ProductId, int quantity) throws SQLException {
        String sql = "UPDATE Product SET stock = stock - ? WHERE id = ? AND stock >= ? FOR UPDATE";
        List<Object> params = new ArrayList<>();
        params.add(quantity);
        params.add(ProductId);
        params.add(quantity);
        return executeUpdate(sql, params);
    }

    // 分页查询商品（按分类、价格范围、排序）
    public List<Product> findProducts(Integer categoryId, Double minPrice, Double maxPrice, String orderBy, int page, int pageSize) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM product WHERE status = 'available'");
        List<Object> params = new ArrayList<>();

        if (categoryId != null) {
            sql.append(" AND category_id = ?");
            params.add(categoryId);
        }
        if (minPrice != null) {
            sql.append(" AND price >= ?");
            params.add(minPrice);
        }
        if (maxPrice != null) {
            sql.append(" AND price <= ?");
            params.add(maxPrice);
        }
        if (orderBy != null) {
            sql.append(" ORDER BY ").append(orderBy);
        }
        sql.append(" LIMIT ? OFFSET ?");
        params.add(pageSize);
        params.add((page - 1) * pageSize);

        return executeQuery(sql.toString(), params);
    }

    // 搜索商品（按关键词）
    public List<Product> searchProducts(String keyword) throws SQLException {
        String sql = "SELECT * FROM product WHERE title LIKE ? OR description LIKE ?";
        String likeKeyword = "%" + keyword + "%";
        return executeQuery(sql, Arrays.asList(likeKeyword, likeKeyword));
    }

    // 新增商品（返回生成的主键）
    public int insertProduct(Product product) throws SQLException, IllegalAccessException {
        return insert(product); // 复用BaseDao的insert方法
    }

    //更新状态
    public int updateStatus(int productId, String status) throws SQLException {
        String sql = "UPDATE product SET status = ? WHERE product_id = ?";
        List<Object> params = new ArrayList<>();
        params.add(status);
        params.add(productId);
        return executeUpdate(sql, params);
    }
}
