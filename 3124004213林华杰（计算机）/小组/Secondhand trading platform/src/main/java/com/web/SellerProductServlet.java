// SellerProductServlet.java
package com.web;

import com.pojo.Product;
import com.pojo.User;
import com.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/seller/product")
public class SellerProductServlet extends HttpServlet {
    private ProductService productService = new ProductService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        // 解析表单数据
        Product product = new Product();
        product.setTitle(req.getParameter("title"));
        product.setDescription(req.getParameter("description"));
        product.setPrice(Double.parseDouble(req.getParameter("price")));
        product.setStock(Integer.parseInt(req.getParameter("stock")));
        product.setCategoryId(Integer.parseInt(req.getParameter("categoryId")));

        boolean success = productService.publishProduct(product, user.getUserId());

        if (success) {
            resp.sendRedirect("seller_products.jsp");
        } else {
            req.setAttribute("error", "商品发布失败");
            req.getRequestDispatcher("publish_product.jsp").forward(req, resp);
        }

    }
    // 处理修改商品请求
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        Product product = parseProductFromRequest(req);
        boolean success = productService.modifyProduct(product, user.getUserId());

        resp.setContentType("application/json");
        resp.getWriter().write("{\"success\":" + success + "}");
    }

    // 处理下架商品请求
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        int productId = Integer.parseInt(req.getParameter("productId"));
        boolean success = productService.deactivateProduct(productId, user.getUserId());

        resp.setContentType("application/json");
        resp.getWriter().write("{\"success\":" + success + "}");
    }

    // 解析请求参数到Product对象
    private Product parseProductFromRequest(HttpServletRequest req) {
        Product product = new Product();
        product.setProductId(Integer.parseInt(req.getParameter("productId")));
        product.setTitle(req.getParameter("title"));
        product.setDescription(req.getParameter("description"));
        product.setPrice(Double.parseDouble(req.getParameter("price")));
        product.setStock(Integer.parseInt(req.getParameter("stock")));
        return product;
    }
}