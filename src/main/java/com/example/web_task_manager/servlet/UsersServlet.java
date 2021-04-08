package com.example.web_task_manager.servlet;

import com.example.web_task_manager.dba.UserDAO;
import com.example.web_task_manager.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class UsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final HttpSession session = req.getSession();
        if (req.getParameter("Logout") != null) {
            session.removeAttribute("password");
            session.removeAttribute("login");
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }
        String login = session.getAttribute("login").toString();
        User user = new UserDAO().getUserByName(login);
        if (!"admin".equals(user.getRole())) {
            System.out.println("Not admin");
            resp.sendRedirect("http://localhost:8888/web_task_manager-1.0-SNAPSHOT/user"); //todo:
        }
        List<User> users = new UserDAO().getAll();

        session.setAttribute("users", users);
        System.out.println("_______________________________________________________________________________" +
                "_____________________________________________________________________________________________________" +
                "_____________________________________________________________________________" +
                "_____________________________________________________________________________" +
                "_____________________________________________________________________________" +
                "_____________________________________________________");
        System.out.println("USERS RAW size = " + users.size());
        users.forEach(us -> System.out.println(us.toString()));
        System.out.println("tostring= "+  session.getAttribute("users").toString());
        System.out.println("LEN= "+ session.getAttribute("users"));
        System.out.println("TRUE IF not null == "+ session.getAttribute("users") != null);
        System.out.println("_______________________________________________________________________________" +
                "_____________________________________________________________________________________________________" +
                "_____________________________________________________________________________" +
                "_____________________________________________________________________________" +
                "_____________________________________________________________________________" +
                "_____________________________________________________");
        getServletContext().getRequestDispatcher("/users.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
