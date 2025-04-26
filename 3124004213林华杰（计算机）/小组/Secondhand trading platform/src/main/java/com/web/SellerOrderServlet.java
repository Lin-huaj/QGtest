package com.web;

import com.pojo.User;
import com.service.OrderService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/seller/order")
public class SellerOrderServlet extends HttpServlet {
    private OrderService orderService = new OrderService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        int orderId = Integer.parseInt(req.getParameter("orderId"));
        String action = req.getParameter("action");

        try {
            boolean success = false;
            switch (action) {
                case "ship":
                    success = orderService.shipOrder(orderId, user.getUserId());
                    break;
                case "cancel":
                    // 卖家取消订单逻辑（需额外校验）
                    break;
                default:
                    resp.sendError(400, "无效操作");
                    return;
            }

            resp.setContentType("application/json");
            resp.getWriter().write("{\"success\":" + success + "}");
        } catch (SQLException e) {
            resp.sendError(500, "操作失败");
        }
    }
}