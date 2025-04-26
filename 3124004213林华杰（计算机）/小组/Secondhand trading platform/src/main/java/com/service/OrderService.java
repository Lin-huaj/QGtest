// OrderService.java
package com.service;

import com.dao.OrderDao;
import com.dao.ProductDao;
import com.pojo.Order;
import com.pojo.Product;
import com.utils.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class OrderService {
    private ConnectionPool pool = ConnectionPool.getInstance();
    private OrderDao orderDao = new OrderDao(pool);
    private ProductDao productDao = new ProductDao(pool);

//    public void beginTransaction() throws SQLException {
//        Connection conn = pool.getConnection();
//        conn.setAutoCommit(false);
//        orderDao.setTransactionConn(conn);
//        productDao.setTransactionConn(conn);
//    }

    // 提交订单（包含库存检查和事务）
    public boolean submitOrder(Order order) {
        try {
            // 开启事务
            orderDao.beginTransaction();
            productDao.beginTransaction();

            // 检查库存是否充足
            int affectedRows = productDao.updateStock(order.getProductId(), order.getQuantity());
            if (affectedRows == 0) {
                throw new SQLException("库存不足");
            }

            // 创建订单
            orderDao.createOrder(order);

            // 提交事务
            orderDao.commitTransaction();
            productDao.commitTransaction();
            return true;
        } catch (Exception e) {
            // 回滚事务
             orderDao.rollbackTransaction();
             productDao.rollbackTransaction();
            e.printStackTrace();
            return false;
        }
    }

    // 完整的下单逻辑
    public boolean createOrder(int buyerId, int productId, int quantity, String paymentMethod) {
        try {
            ProductDao productDao = new ProductDao(pool);
            OrderDao orderDao = new OrderDao(pool);

            // 开启事务
            productDao.beginTransaction();
            orderDao.beginTransaction();

            // 1. 检查商品状态和库存
            Product product = productDao.findById(productId);
            if (product == null || !product.getStatus().equals(Product.ProductStatus.available)) {
                throw new SQLException("商品不可用");
            }
            if (product.getStock() < quantity) {
                throw new SQLException("库存不足");
            }

            // 2. 扣减库存
            int updateRows = productDao.updateStock(productId, quantity);
            if (updateRows == 0) {
                throw new SQLException("库存更新失败");
            }

            // 3. 创建订单
            Order order = new Order();
            order.setBuyer_id(buyerId);
            order.setProductId(productId);
            order.setQuantity(quantity);
            order.setTotalPrice(product.getPrice() * quantity);
            order.setStatus(Order.OrderStatus.pending);
            order.setPaymentMethod(Order.Paymentmethod.valueOf(paymentMethod));
            order.setCreatedAt(new Date());

            int orderId = orderDao.createOrder(order);

            // 提交事务
            productDao.commitTransaction();
            orderDao.commitTransaction();
            return orderId > 0;
        } catch (Exception e) {
            // 回滚事务
            try { productDao.rollbackTransaction(); } catch (Exception ignored) {}
            try { orderDao.rollbackTransaction(); } catch (Exception ignored) {}
            return false;
        }
    }
    // 更新订单状态（供管理员或卖家调用）
    public boolean updateOrderStatus(int orderId, Order.OrderStatus newStatus) throws SQLException {
        OrderDao orderDao = new OrderDao(pool);
        try {
            orderDao.beginTransaction();
            int rows = orderDao.updateStatus(orderId, newStatus);
            orderDao.commitTransaction();
            return rows > 0;
        } catch (SQLException e) {
            orderDao.rollbackTransaction();
            throw e;
        }
    }

    // 卖家发货（需校验订单属于卖家）
    public boolean shipOrder(int orderId, int sellerId) throws SQLException {
        OrderDao orderDao = new OrderDao(pool);
        Order order = orderDao.findById(orderId);
        if (order == null) return false;

        ProductDao productDao = new ProductDao(pool);
        Product product = productDao.findById(order.getProductId());
        if (product == null || product.getUserId() != sellerId) {
            return false;
        }

        return updateOrderStatus(orderId, Order.OrderStatus.shipped);
    }

}