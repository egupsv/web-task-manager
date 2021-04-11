package com.example.web_task_manager.servlet;

import com.example.web_task_manager.dba.UserDAO;
import com.example.web_task_manager.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServlet extends AuthServletTemplate {

    public UserServlet() {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);


        String pathInfo = req.getPathInfo();
        String targetUser = req.getSession().getAttribute("login").toString();
        if (pathInfo != null)
            targetUser = pathInfo.substring(1).trim();
        else{
            resp.sendRedirect("http://localhost:8888/web_task_manager-1.0-SNAPSHOT/user/" + user.getName());
        }
        req.setAttribute("tar_user", targetUser);

        getServletContext().getRequestDispatcher("/user.jsp").forward(req, resp);
    }
}
