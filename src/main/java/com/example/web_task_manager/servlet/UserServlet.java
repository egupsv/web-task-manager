package com.example.web_task_manager.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServlet extends AuthServletTemplate {
    private static final String DELETE_PARAM = "delete";

    public UserServlet() {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);

        req.setAttribute("tar_user", user);

        getServletContext().getRequestDispatcher("/user.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        super.doPost(req, resp);

        String deleteStatusString = req.getParameter(DELETE_PARAM).trim();
        if (deleteStatusString.length() > 0) {
            int deleteStatus = Integer.parseInt(deleteStatusString);
            if (deleteStatus == 1) {
                logoutUser(req, resp);
                userDAO.delete(user.getId());
                return;
            }
        }
        resp.sendRedirect(req.getContextPath() + "/user");
    }
}
