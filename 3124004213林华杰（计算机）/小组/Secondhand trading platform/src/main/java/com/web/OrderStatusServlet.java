// OrderStatusServlet.java
package com.web;

import com.pojo.Order;
import com.pojo.User;
import com.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/orderStatus")
public class OrderStatusServlet extends HttpServlet {
    private OrderService orderService = new OrderService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        int orderId = Integer.parseInt(req.getParameter("orderId"));

        try {
            switch (action) {
                case "confirmDelivery":
                    orderService.updateOrderStatus(orderId, Order.OrderStatus.shipped);
                    break;
                case "completeOrder":
                    orderService.updateOrderStatus(orderId, Order.OrderStatus.completed);
                    break;
                case "cancelOrder":
                    orderService.updateOrderStatus(orderId, Order.OrderStatus.canceled);
                    break;
                default:
                    resp.sendError(400, "无效操作");
                    return;
            }
            resp.sendRedirect("order_detail.jsp?orderId=" + orderId);
        } catch (SQLException e) {
            resp.sendError(500, "状态更新失败");
        }
    }
}