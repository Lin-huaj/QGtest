// OrderServlet.java
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

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    private OrderService orderService = new OrderService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        int productId = Integer.parseInt(req.getParameter("productId"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        String paymentMethod = req.getParameter("paymentMethod");

        boolean success = orderService.createOrder(
                user.getUserId(), productId, quantity, paymentMethod
        );

        if (success) {
            resp.sendRedirect("order_success.jsp");
        } else {
            req.setAttribute("error", "下单失败，请检查库存或商品状态");
            req.getRequestDispatcher("product_detail.jsp").forward(req, resp);
        }
    }
}