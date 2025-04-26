// OrderDao.java
package com.dao;

import com.pojo.Order;
import com.utils.ConnectionPool;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OrderDao extends BaseDao<Order> {
    public OrderDao(ConnectionPool pool) {
        super(pool, Order.class);
    }

    // 根据买家ID查询订单
    public List<Order> findByBuyerId(int buyerId) throws SQLException {
        String sql = "SELECT * FROM `order` WHERE buyer_id = ?";
        return executeQuery(sql, Collections.singletonList(buyerId));
    }

    // 更新订单状态（如支付、发货）
    public int updateStatus(int orderId, Order.OrderStatus status) throws SQLException {
        String sql = "UPDATE `order` SET status = ? WHERE order_id = ?";
        List<Object> params = new ArrayList<>();
        params.add(status.toString());
        params.add(orderId);
        return executeUpdate(sql, params);
    }

    // 创建订单（需事务支持）
    public int createOrder(Order order) throws SQLException, IllegalAccessException {
        return insert(order);
    }

    // 根据订单ID查询订单
    public Order findById(int orderId) throws SQLException {
        String sql = "SELECT * FROM `order` WHERE order_id = ?";
        List<Order> orders = executeQuery(sql, Collections.singletonList(orderId));
        return orders.isEmpty() ? null : orders.get(0);
    }

    // 更新支付方式
    public int updatePaymentMethod(int orderId, Order.Paymentmethod method) throws SQLException {
        String sql = "UPDATE `order` SET payment_method = ? WHERE order_id = ?";
        return executeUpdate(sql, Arrays.asList(method.toString(), orderId));
    }
}