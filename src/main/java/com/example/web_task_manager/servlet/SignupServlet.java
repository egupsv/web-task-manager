package com.example.web_task_manager.servlet;

import com.example.web_task_manager.users.User;
import com.example.web_task_manager.users.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SignupServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(SignupServlet.class);
    @Override public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

//hardcode for user, to replace with DB
        Users users = new Users();
        users.addUser(new User("a", "qwerty"));

        User user = new User(login, password);
        if (users.getUser(login) != null) {
            log.info("this login is already taken");
            request.getSession().setAttribute("attempt", "wrong");
            TryChecker.setPropertyOfSignupDiv("block");
            response.sendRedirect("signup.jsp");
        }
        else {
            users.addUser(user);
            log.info("User has logged in");
            request.getSession().setAttribute("attempt", null);
            TryChecker.setPropertyOfSignupDiv("none");
            request.getSession().setAttribute("login", login);
            response.sendRedirect("/web_task_manager-1.0-SNAPSHOT/tasks");
        }
    }
}
