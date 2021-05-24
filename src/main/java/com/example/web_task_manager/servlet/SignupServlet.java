package com.example.web_task_manager.servlet;

import com.example.web_task_manager.CookieName;
import com.example.web_task_manager.Properties;

import com.example.web_task_manager.model.User;
import com.example.web_task_manager.servlet.template.ServletTemplate;
import com.example.web_task_manager.users.Encryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class SignupServlet extends ServletTemplate {
    private static final Logger log = LoggerFactory.getLogger(SignupServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().removeAttribute("wrong");
        resp.sendRedirect(req.getContextPath() + "/signup.jsp");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("taken");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String mail = request.getParameter("mail");
        if (userDAO.getUserByName(login) != null || userDAO.getUserByMail(mail) != null) {
            log.info("this login is already taken");
            String message = "this login is already taken";
            request.getSession().setAttribute("taken", message);
            response.sendRedirect("signup.jsp");
        } else if (Properties.REGEX_LOGIN_PATTERN.matcher(login).find() && Properties.REGEX_MAIL_PATTERN.matcher(mail).find()) {
            String encPassword = null;
            try {
                encPassword = new Encryptor().encrypt(password);
            } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            User user = new User(login, encPassword, mail);
            userDAO.create(user);
            log.info("User has logged in");
            request.getSession().setAttribute("login", login);
            request.getSession().setAttribute("role", user.getRole());
            cookieController.createCookie(response, CookieName.LOGIN, login);
            response.sendRedirect(request.getContextPath() + "/tasks/" + user.getName());
        } else {
            log.info("this login + " + login + " is incorrect");
            log.info(" or this mail + " + mail + " is incorrect");
            response.sendRedirect("signup.jsp");
        }
    }

}
