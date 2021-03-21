package com.example.web_task_manager.servlet;

import com.example.web_task_manager.dba.UserDAO;
import com.example.web_task_manager.model.User;
import com.example.web_task_manager.users.Encryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;


public class LoginServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    public void init() throws ServletException {
        super.init();
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
        User targetUser = new UserDAO().getUserByName(login);
        if (targetUser != null &&
                encPassword.equals(targetUser.getEncPassword())) user = targetUser;
        if (user != null) {
            log.info("User has logged in");
            request.getSession().setAttribute("login", login);
            TryChecker.setPropertyOfLoginDiv("none");
            request.getSession().setAttribute("attempt", null);
            response.sendRedirect(request.getContextPath() + "/tasks");
        } else {
            log.info("login or password is incorrect");
            request.getSession().setAttribute("attempt", "wrong");
            response.sendRedirect("login.jsp");
            TryChecker.setPropertyOfLoginDiv("block");

        }
    }
}
