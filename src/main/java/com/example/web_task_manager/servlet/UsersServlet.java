package com.example.web_task_manager.servlet;

import com.example.web_task_manager.Properties;
import com.example.web_task_manager.dba.UserDAO;
import com.example.web_task_manager.model.User;
import com.example.web_task_manager.users.Encryptor;
import com.sun.istack.NotNull;

import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class UsersServlet extends AuthServletTemplate {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        if (!isAdmin) {
            resp.sendRedirect("http://localhost:8888/web_task_manager-1.0-SNAPSHOT/user");
            return;
        }

        setUsersParameter(req);
        getServletContext().getRequestDispatcher("/users.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //String login = req.getSession().getAttribute("login").toString();

        //boolean isAdmin = Role.ADMIN.toString().equals(user.getRole());
        if (!isAdmin) {
            resp.sendRedirect(req.getContextPath() + "/user");
            return;
        }

        editParamCheck(req);
        deleteParamCheck(req);

        resp.sendRedirect(req.getContextPath() + "/users");
    }

    private void deleteParamCheck(HttpServletRequest req) {
        String deleteParam = req.getParameter("delete");
        int deleteId = deleteParam == null ? 0 : Integer.parseInt(deleteParam.trim());

        if (deleteId > 0) {
            User targetUser = new UserDAO().getEntityById(deleteId);
            if (!targetUser.getName().equals(req.getSession().getAttribute("login")))
                new UserDAO().delete(targetUser.getId());
        }
    }

    public void editParamCheck(HttpServletRequest req) {
        String editIdParam = req.getParameter("submit_edit_b");
        int editableId = editIdParam == null ? 0 : Integer.parseInt(editIdParam.trim());
        if (editableId > 0) {
            User targetUser = new UserDAO().getEntityById(editableId);
            String newName = req.getParameter("name").trim();
            String newMail = req.getParameter("mail").trim();
            String newPassword = req.getParameter("password").trim();
            String newRole = req.getParameter("role").trim();

            if (newRole.length() > 0 && !targetUser.getName().equals(req.getSession().getAttribute("login")))
                targetUser.setRole(newRole);
            if (newName.length() > 0)
                targetUser.setName(newName);

            if (newPassword.length() > 0) {
                try {
                    String encPassword = new Encryptor().encrypt(newPassword);
                    targetUser.setEncPassword(encPassword);
                } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
            if (Properties.REGEX_MAIL_PATTERN.matcher(newMail).find())
                targetUser.setMail(newMail);

            new UserDAO().update(targetUser);
        }
    }

    public void setUsersParameter(@NotNull HttpServletRequest req) {
        List<User> users;
        //if (req.getParameter("filtered_users") == null)
        users = new UserDAO().getAll();
        req.setAttribute("users", users);
    }

}
