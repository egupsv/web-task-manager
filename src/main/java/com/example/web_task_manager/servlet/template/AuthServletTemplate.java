package com.example.web_task_manager.servlet.template;

import com.example.web_task_manager.CookieName;
import com.example.web_task_manager.servlet.TaskServlet;
import com.example.web_task_manager.users.Role;
import com.example.web_task_manager.dba.TaskDAO;
import com.example.web_task_manager.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthServletTemplate extends ServletTemplate {
    private static final Logger log = LoggerFactory.getLogger(AuthServletTemplate.class);
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

    }

    private boolean loginCheck(HttpServletRequest req) {
        return req.getParameter("Logout") == null;
    }

    protected void logoutUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("LOGOUT USER AUTH");
        req.getSession().removeAttribute("login");
        req.getSession().removeAttribute("role");
        cookieController.eraseAllCookies(req, resp);
        resp.sendRedirect(req.getContextPath() + "/login.jsp");
        log.info("login " + req.getSession().getAttribute("login"));
        log.info("login " + cookieController.getCookieValue(req, CookieName.LOGIN));
        log.info("/LOGOUT USER AUTH");
    }

}
