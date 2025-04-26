// AdminServlet.java
package com.web;

import com.dao.ProductDao;
import com.dao.UserDao;
import com.utils.ConnectionPool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("banUser".equals(action)) {
            int userId = Integer.parseInt(req.getParameter("userId"));
            // 封禁用户
            UserDao userDao = new UserDao(ConnectionPool.getInstance());
            try {
                userDao.updateStatus(userId, "banned");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if ("deleteProduct".equals(action)) {
            int productId = Integer.parseInt(req.getParameter("productId"));
            // 删除商品
            ProductDao productDao = new ProductDao(ConnectionPool.getInstance());
            try {
                productDao.delete(productId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}