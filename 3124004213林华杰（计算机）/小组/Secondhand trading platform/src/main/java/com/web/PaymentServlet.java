// PaymentServlet.java
package com.web;

import com.pojo.User;
import com.service.PaymentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {
    private PaymentService paymentService = new PaymentService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        int orderId = Integer.parseInt(req.getParameter("orderId"));
        String paymentMethod = req.getParameter("paymentMethod");
        double amount = Double.parseDouble(req.getParameter("amount"));

        boolean success = paymentService.processPayment(orderId, paymentMethod, amount);

        if (success) {
            resp.sendRedirect("payment_success.jsp?orderId=" + orderId);
        } else {
            req.setAttribute("error", "支付失败，请重试");
            req.getRequestDispatcher("payment.jsp").forward(req, resp);
        }
    }
}