package com.example.web_task_manager.servlet;

import com.example.web_task_manager.Properties;
import com.example.web_task_manager.dba.UserDAO;
import com.example.web_task_manager.model.User;
import com.example.web_task_manager.users.Encryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class SignupServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(SignupServlet.class);
    private final UserDAO userDAO = new UserDAO();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String mail = request.getParameter("mail");
        if (userDAO.getUserByName(login) != null || userDAO.getUserByMail(mail) != null) {
            log.info("this login is already taken");
            request.getSession().setAttribute("attempt", "wrong");
            TryChecker.setPropertyOfSignupDiv("block");
            response.sendRedirect("signup.jsp");
        } else if (Properties.REGEX_LOGIN_PATTERN.matcher(login).find() && Properties.REGEX_MAIL_PATTERN.matcher(mail).find()) {
            String encPassword = null;
            try {
                encPassword = new Encryptor().encrypt(password);
            } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            User user = new User(login, encPassword, mail, "user");
            userDAO.create(user);
            log.info("User has logged in");
            request.getSession().setAttribute("attempt", null);
            TryChecker.setPropertyOfSignupDiv("none");
            request.getSession().setAttribute("login", login);
            response.sendRedirect(request.getContextPath() + "/tasks");
        } else {
            log.info("this login + " + login + " is incorrect");
            log.info(" or this mail + " + mail + " is incorrect");
            response.sendRedirect("signup.jsp");
        }
    }

}
