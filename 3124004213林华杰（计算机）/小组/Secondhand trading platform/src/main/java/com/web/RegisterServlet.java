package com.web;

import com.pojo.User;
import com.service.UserService;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String username = request.getParameter("nickname");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String studentId = request.getParameter("studentId");

        try {
            // 检查用户名是否已存在
            List<User> existingUser = userService.findByUsername(username);
            if (!existingUser.isEmpty()) {
                request.setAttribute("error", "用户名已存在");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            // 密码加密（使用BCrypt）
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            // 创建用户对象
            User user = new User();
            user.setNickname(username);
            user.setPassword(hashedPassword);
            user.setEmail(email);
            user.setPhone(phone);
            user.setStudentId(studentId);

            // 保存用户
            int result = userService.addUser(user);
            if (result > 0) {
                response.sendRedirect("login.jsp"); // 注册成功跳转到登录页
            } else {
                request.setAttribute("error", "注册失败，请重试");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        } catch (SQLException e ) {
            e.printStackTrace(); // 添加此行打印堆栈信息
            request.setAttribute("error", "系统错误1，请联系管理员");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }catch ( IllegalAccessException e) {
            request.setAttribute("error", "系统错误2，请联系管理员");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}