package com.web;

import com.dao.ProductDao;
import com.utils.ConnectionPool;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@WebServlet("/admin/product")
public class AdminProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        int productId = Integer.parseInt(req.getParameter("productId"));
        String action = req.getParameter("action");
        ProductDao productDao = new ProductDao(ConnectionPool.getInstance());
        if ("approve".equals(action)) {
            try {
                productDao.updateStatus(productId, "approved");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if ("reject".equals(action)) {
            try {
                productDao.delete(productId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
