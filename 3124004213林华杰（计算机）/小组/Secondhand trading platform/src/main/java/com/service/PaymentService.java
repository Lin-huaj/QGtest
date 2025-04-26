// PaymentService.java
package com.service;

import com.dao.OrderDao;
import com.dao.ProductDao;
import com.pojo.Order;
import com.utils.ConnectionPool;
import java.sql.SQLException;

public class PaymentService {
    private ConnectionPool pool = ConnectionPool.getInstance();
    private OrderDao orderDao = new OrderDao(pool);

    // 模拟支付处理（返回支付是否成功）
    public boolean processPayment(int orderId, String paymentMethod, double amount) {
        try {
            // 开启事务
            orderDao.beginTransaction();

            // 1. 获取订单信息
            Order order = orderDao.findById(orderId);
            if (order == null || !order.getStatus().equals(Order.OrderStatus.pending)) {
                throw new SQLException("订单无效或已处理");
            }

            // 2. 模拟支付逻辑（此处仅做示例）
            boolean paymentSuccess = simulatePayment(paymentMethod, amount);

            // 3. 更新订单状态
            if (paymentSuccess) {
                order.setStatus(Order.OrderStatus.paid);
                orderDao.updateStatus(orderId, Order.OrderStatus.paid);
                orderDao.commitTransaction();

                //支付成功后更新商品库存
                ProductDao productDao = new ProductDao(pool);
                productDao.updateStock(order.getProductId(), order.getQuantity());
                return true;
            } else {
                orderDao.rollbackTransaction();
                return false;
            }
        } catch (Exception e) {
            orderDao.rollbackTransaction();
            e.printStackTrace();
            return false;
        }
    }

    // 模拟支付结果（实际应调用第三方支付接口）
    private boolean simulatePayment(String method, double amount) {
        // 示例逻辑：金额大于0时成功
        return amount > 0;
    }
}