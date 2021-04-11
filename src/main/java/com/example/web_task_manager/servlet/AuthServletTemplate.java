package com.example.web_task_manager.servlet;

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final HttpSession session = req.getSession();
        if (!loginCheck(req, resp))
            return;
        user = userDAO.getUserByName(session.getAttribute("login").toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    }

    private boolean loginCheck(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameter("Logout") != null) { //todo: ques? if we have LoginFilter, then why we need this?
            req.getSession().removeAttribute("password");
            req.getSession().removeAttribute("login");
            resp.sendRedirect("/login.jsp");
            return false;
        }
        return true;
    }
}
