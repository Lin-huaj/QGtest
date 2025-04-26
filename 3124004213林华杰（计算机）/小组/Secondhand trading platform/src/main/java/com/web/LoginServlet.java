package com.web;


import com.pojo.User;
import com.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    UserService service = new UserService();  // 创建 service 对象

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 设置中文编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // 获取请求参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //用户名密码不为空
        if (username == null || password == null||username.trim().isEmpty()) {
            request.setAttribute("error","用户名和密码不能为空");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }

        try {
            User user = service.login(username, password);  // 调用方法
            if(user!=null){
                HttpSession session = request.getSession();
                session.setAttribute("user",user);
                response.sendRedirect("home.jsp");

                //创建Cookie
                Cookie c_username=new Cookie("username",username);
                Cookie c_password =new Cookie("password",password);
                //设置账号密码保存时间长度
                c_username.setMaxAge(60*60*24*7);
                c_password.setMaxAge(60*60*24*7);
                //发送Cookie保存
                response.addCookie(c_username);
                response.addCookie(c_password);
            }
            else {
                // 登录失败：返回错误信息
                request.setAttribute("error", "用户名或密码错误");
                RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response); // 服务器端跳转
            }
        } catch (SQLException e) {
            request.setAttribute("error","系统错误，请联系管理员");
            request.getRequestDispatcher("login.jsp").forward(request,response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        doPost(request, response);
    }
}