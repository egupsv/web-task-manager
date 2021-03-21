package com.example.web_task_manager.servlet;

import com.example.web_task_manager.dba.UserDAO;
import com.example.web_task_manager.model.User;

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
//        String pathInfo = request.getPathInfo();
//        String uName = pathInfo.substring(1);
        User user = new UserDAO().getUserByName(request.getParameter("login"));
        request.setAttribute("target_user", user);
        if (user == null) {
            System.out.println("target USER is not found");
            request.removeAttribute("target_user");
            getServletContext().getRequestDispatcher(request.getParameter("login")).forward(request, response);
        }
        //getServletContext().getRequestDispatcher("/user/" + uName).forward(request, response);
        //response.sendRedirect(request.getContextPath() + "/user/" + uName);
        //request.setAttribute("user", new UserDAO().getUserByName(request.getParameter("")));
        //super.doGet(request, response);
    }
}
