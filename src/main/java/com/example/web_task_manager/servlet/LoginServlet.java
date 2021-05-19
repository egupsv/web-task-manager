package com.example.web_task_manager.servlet;

import com.example.web_task_manager.CookieName;
import com.example.web_task_manager.model.User;
import com.example.web_task_manager.servlet.template.ServletTemplate;
import com.example.web_task_manager.users.Encryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;


public class LoginServlet extends ServletTemplate {
    private static final Logger log = LoggerFactory.getLogger(LoginServlet.class);


    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("taken");
        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String encPassword = null;
        try {
            encPassword = new Encryptor().encrypt(password);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        log.info(encPassword);
        User user = null;
        User targetUser = userDAO.getUserByName(login);
        if (targetUser != null &&
                encPassword.equals(targetUser.getEncPassword()))
            user = targetUser;

        if (user != null) {
            request.getSession().setAttribute("login", login);
            request.getSession().setAttribute("role", user.getRole());
            cookieController.createCookie(response, CookieName.LOGIN, user.getName());
            response.sendRedirect("/web_task_manager-1.0-SNAPSHOT/tasks");
            log.info("User has logged in");
        } else {
            String message = "<p>login or password is incorrect</p>";
            request.getSession().setAttribute("wrong", message);
            response.sendRedirect("login.jsp");
        }
    }
}
