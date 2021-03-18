package com.example.web_task_manager.servlet;

import com.example.web_task_manager.Properties;
import com.example.web_task_manager.controller.NotifyWorker;
import com.example.web_task_manager.dba.UserDAO;
import com.example.web_task_manager.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignupServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(SignupServlet.class);
    UserDAO userDAO = new UserDAO();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (NotifyWorker.INSTANCE == null) {
            NotifyWorker.INSTANCE = new NotifyWorker();
            NotifyWorker.INSTANCE.run();
        }
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String mail = request.getParameter("mail");

        if (userDAO.getUserByName(login) != null || userDAO.getUserByMail(mail) != null) {
            log.info("this login is already taken");
            request.getSession().setAttribute("attempt", "wrong");
            TryChecker.setPropertyOfSignupDiv("block");
            response.sendRedirect("signup.jsp");
        } else if (Properties.REGEX_LOGIN_PATTERN.matcher(login).find() && Properties.REGEX_MAIL_PATTERN.matcher(mail).find()) {
            User user = new User(login, password, mail);
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
