package com.example.web_task_manager.servlet;

import com.example.web_task_manager.dba.UserDAO;
import com.example.web_task_manager.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserServlet extends HttpServlet {

    public UserServlet() {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("Logout") != null) {
            final HttpSession session = request.getSession();
            session.removeAttribute("password");
            session.removeAttribute("login");
            getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        String login = request.getSession().getAttribute("login").toString();
        User user = new UserDAO().getUserByName(login);
        request.getSession().setAttribute("tar_user", user);

        System.out.println("a");

        RequestDispatcher rd = request.getRequestDispatcher("/user.jsp");
        rd.forward(request, response);
    }
}
