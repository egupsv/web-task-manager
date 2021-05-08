package com.example.web_task_manager.servlet.template;

import com.example.web_task_manager.CookieName;
import com.example.web_task_manager.users.Role;
import com.example.web_task_manager.dba.TaskDAO;
import com.example.web_task_manager.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthServletTemplate extends ServletTemplate {
    protected final TaskDAO taskDAO = new TaskDAO();
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

    private boolean loginCheck(HttpServletRequest req) {
        return req.getParameter("Logout") == null;
    }

    protected void logoutUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("LOGOUT USER AUTH");
        req.getSession().removeAttribute("password");
        req.getSession().removeAttribute("login");
        req.getSession().removeAttribute("role");
        cookieController.eraseCookie(resp, CookieName.LOGIN);
        cookieController.eraseCookie(resp, CookieName.PASSWORD);
        resp.sendRedirect(req.getContextPath() + "/login.jsp");
        System.out.println("/LOGOUT USER AUTH");
    }

}
