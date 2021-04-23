package com.example.web_task_manager.servlet;

import com.example.web_task_manager.users.Encryptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServlet extends AuthServletTemplate {
    private static final String DELETE_PARAM = "delete";
    private static final String CHANGE_PASS_PARAM = "chg_pass";

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

        if (checkDeleteStatus(req, resp))
            return;
        checkEditPassParam(req);

        resp.sendRedirect(req.getContextPath() + "/user");
    }

    private boolean checkDeleteStatus(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String deleteStatusParam = req.getParameter(DELETE_PARAM);
        String deleteStatusString = deleteStatusParam == null ? "" : deleteStatusParam.trim();
        if (deleteStatusString.length() > 0) {
            int deleteStatus = Integer.parseInt(deleteStatusString);
            if (deleteStatus == 1) {
                logoutUser(req, resp);
                userDAO.delete(user.getId());
                return true;
            }
        }
        return false;
    }

    private void checkEditPassParam(HttpServletRequest req) {
        String chgPassParam = req.getParameter(CHANGE_PASS_PARAM);
        String newPassString = chgPassParam == null ? "" : chgPassParam.trim();
        if (newPassString.length() > 0) { //need condition
            try {
                String newPass = new Encryptor().encrypt(newPassString);
                user.setEncPassword(newPass);
                userDAO.update(user);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
