package com.example.web_task_manager.servlet;

import com.example.web_task_manager.Role;
import com.example.web_task_manager.dba.TaskDAO;
import com.example.web_task_manager.dba.UserDAO;
import com.example.web_task_manager.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthServletTemplate extends HttpServlet {
    protected final TaskDAO taskDAO = new TaskDAO();
    protected final UserDAO userDAO = new UserDAO();
    protected User user;


    protected boolean isAdmin = false;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final HttpSession session = req.getSession();
        if (!loginCheck(req)) {
            logoutUser(req, resp);
            return;
        }

        user = userDAO.getUserByName(session.getAttribute("login").toString());
        isAdmin = Role.ADMIN.toString().equals(user.getRole());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    }

    private boolean loginCheck(HttpServletRequest req) { //todo: ques? if we have LoginFilter, then why we need this?
        return req.getParameter("Logout") == null;
    }

    protected void logoutUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().removeAttribute("password");
        req.getSession().removeAttribute("login");
        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }

}
