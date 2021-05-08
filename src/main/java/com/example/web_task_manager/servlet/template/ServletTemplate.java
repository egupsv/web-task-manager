package com.example.web_task_manager.servlet.template;

import com.example.web_task_manager.controller.CookieController;
import com.example.web_task_manager.dba.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ServletTemplate extends HttpServlet {
    protected final String EMPTY_VALUE = "";
    protected final UserDAO userDAO = new UserDAO();

    protected final CookieController cookieController = new CookieController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
