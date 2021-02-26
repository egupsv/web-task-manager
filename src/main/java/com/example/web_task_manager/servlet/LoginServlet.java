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

public class LoginServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(LoginServlet.class);
    @Override public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        Users users = new Users();
        users.addUser(new User("a", "qwerty"));
        User user = null;
        if (users.getUser(login) != null && password.equals(users.getUser(login).getEncPassword())) user = users.getUser(login);
        if(user != null) {
            log.info("User %s has logged in");
            request.getSession().setAttribute("login", login);
            //request.getSession().setAttribute("password", password);
            request.getRequestDispatcher("/tasks.jsp").forward(request, response);
            response.sendRedirect("/tasks.jsp");
        }
        else {
            log.info("login or password is incorrect");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
