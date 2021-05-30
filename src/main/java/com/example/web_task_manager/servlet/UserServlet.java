package com.example.web_task_manager.servlet;

import com.example.web_task_manager.CookieName;
import com.example.web_task_manager.constants.Constants;
import com.example.web_task_manager.servlet.template.AuthServletTemplate;
import com.example.web_task_manager.users.Encryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServlet extends AuthServletTemplate {
    private static final Logger log = LoggerFactory.getLogger(AuthServletTemplate.class);
    public UserServlet() {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        req.getSession().setAttribute(Constants.INVALID_U_ATTRIBUTE, null);
        req.getSession().setAttribute(Constants.INVALID_ATTRIBUTE, null);
        req.getSession().setAttribute(Constants.EXISTED_ATTRIBUTE, null);
        req.getSession().setAttribute(Constants.EXPORT_FAIL, null);
        req.setAttribute("tar_user", user);

        getServletContext().getRequestDispatcher("/user.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        super.doPost(req, resp);

        if (checkDeleteStatus(req, resp))
            return;
        checkEditPassParam(req, resp);

        resp.sendRedirect(req.getContextPath() + "/user");
    }

    private boolean checkDeleteStatus(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        if (req.getParameter(Constants.DELETE_PARAM) != null) {
            logoutUser(req, resp);
            userDAO.delete(user.getId());
            return true;
        }
        return false;
    }

    private void checkEditPassParam(HttpServletRequest req, HttpServletResponse resp) {
        String chgPassParam = req.getParameter(Constants.CHANGE_PASS_PARAM);
        String newPassString = chgPassParam == null ? Constants.EMPTY_VALUE : chgPassParam.trim();
        if (!newPassString.isEmpty()) {
            try {
                String newPass = new Encryptor().encrypt(newPassString);
                user.setEncPassword(newPass);
                userDAO.update(user);
                cookieController.createCookie(resp, CookieName.PASSWORD, newPass);
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
    }
}
