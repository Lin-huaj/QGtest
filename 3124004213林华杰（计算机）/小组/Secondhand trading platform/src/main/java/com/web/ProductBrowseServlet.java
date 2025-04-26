//ProductBrowseServlet.java
package com.web;

import com.dao.ProductDao;
import com.pojo.Product;
import com.utils.ConnectionPool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/Browse/product")
public class ProductBrowseServlet extends HttpServlet {
    private ProductDao productDao = new ProductDao(ConnectionPool.getInstance());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("search".equals(action)) {
                // 搜索商品
                String keyword = req.getParameter("keyword");
                List<Product> products = productDao.searchProducts(keyword);
                req.setAttribute("products", products);
            } else {
                // 分页浏览商品
                String pageParam = req.getParameter("page");
                int page = (pageParam != null && !pageParam.isEmpty())
                        ? Integer.parseInt(pageParam)
                        : 1; // 默认值
                int pageSize = 10;
                Integer categoryId = parseParam(req.getParameter("categoryId"));
                Double minPrice = parseParam(req.getParameter("minPrice"));
                Double maxPrice = parseParam(req.getParameter("maxPrice"));
                String orderBy = req.getParameter("orderBy");

                List<Product> products = productDao.findProducts(
                        categoryId, minPrice, maxPrice, orderBy, page, pageSize
                );
                req.setAttribute("products", products);
            }
            req.getRequestDispatcher("/product_list.jsp").forward(req, resp);
        } catch (SQLException | NumberFormatException e) {
            resp.sendError(500, "商品加载失败");
        }
    }

    private <T> T parseParam(String value) {
        return value != null ? (T) value : null;
    }
}