// CreditServlet.java
package com.web;

import com.pojo.User;
import com.service.UserService;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/credit")
public class CreditServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        int userId = Integer.parseInt(request.getParameter("userId"));

        if ("update".equals(action)) {
            int newScore = Integer.parseInt(request.getParameter("score"));
            boolean success = userService.updateUserCredit(userId, newScore);
            response.getWriter().write(success ? "信用更新成功" : "更新失败");
        }
    }
}