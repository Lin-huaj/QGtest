// ProductService.java
package com.service;

import com.dao.ProductDao;
import com.pojo.Product;
import com.utils.ConnectionPool;
import java.sql.SQLException;

public class ProductService {
    private ConnectionPool pool = ConnectionPool.getInstance();
    private ProductDao productDao = new ProductDao(pool);

    // 发布商品
    public boolean publishProduct(Product product, int sellerId) {
        try {
            // 设置卖家ID和默认状态
            productDao.beginTransaction();
            product.setUserId(sellerId);
            product.setStatus(Product.ProductStatus.available);
            int rows = productDao.insertProduct(product);
            productDao.commitTransaction();
            return rows > 0;
        } catch (SQLException | IllegalAccessException e) {
            productDao.rollbackTransaction();
            e.printStackTrace();
            return false;
        }
    }
    //商品删除和下架功能
    public boolean deactivateProduct(int productId, int sellerId) {
        try {
            Product product = productDao.findById(productId);
            if (product == null || product.getUserId() != sellerId) return false;
            product.setStatus(Product.ProductStatus.deleted);
            return productDao.update(product) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean modifyProduct(Product product, int sellerId) {
        try {
            // 1. 验证用户是否有权限修改商品
            Product existingProduct = productDao.findById(product.getProductId());
            if (existingProduct == null || existingProduct.getUserId() != sellerId) {
                return false;
            }

            // 2. 更新商品信息
            productDao.beginTransaction();
            int rows = productDao.update(product);
            productDao.commitTransaction();
            return rows > 0;
        } catch (SQLException | IllegalAccessException e) {
            productDao.rollbackTransaction();
            e.printStackTrace();
            return false;
        }
    }
}