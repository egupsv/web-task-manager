package com.example.web_task_manager.servlet;

import com.example.web_task_manager.Properties;
import com.example.web_task_manager.converter.Converter;
import com.example.web_task_manager.dba.UserDAO;
import com.example.web_task_manager.model.Task;
import com.example.web_task_manager.model.User;
import com.example.web_task_manager.servlet.template.AuthServletTemplate;
import com.example.web_task_manager.users.Encryptor;
import com.sun.istack.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.NoSuchPaddingException;
import javax.ejb.EJB;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class UsersServlet extends AuthServletTemplate {
    private static final String NEW_USER_PARAM = "new_user_check";
    private static final String EXPORT_PARAM = "export";
    private static final Logger log = LoggerFactory.getLogger(UsersServlet.class);

    @EJB
    private Converter ejb;

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
        if (!isAdmin) {
            resp.sendRedirect(req.getContextPath() + "/user");
            return;
        }
        if (!editParamCheck(req)) {
            System.out.println("POST CREATE USER CHECK");
            createUser(req);
        }
        deleteParamCheck(req);
        if (req.getParameter(EXPORT_PARAM) != null) {

            String parameter = req.getParameter(EXPORT_PARAM);
            String fileName = "users.xml";
            List<User> users = new ArrayList<>();
            if(parameter.equals("all")) {
                users = userDAO.getAll();
            } else {
                int exportedUserID = Integer.parseInt(parameter);
                User user = userDAO.getEntityById(exportedUserID);
                users.add(user);
                fileName = "user" + user.getId() + ".xml";
            }
            resp.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");
            resp.setContentType("text/xml; name=\"fileName\"");
            ejb.convertObjectToXml(users, null, fileName, resp);
        }
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

    public boolean editParamCheck(HttpServletRequest req) {
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
            if (newName.length() > 0 && !newName.equals(targetUser.getName()))
                targetUser.setName(newName);

            if (newPassword.length() > 0) {
                try {
                    String encPassword = new Encryptor().encrypt(newPassword);
                    if (!encPassword.equals(targetUser.getEncPassword()))
                        targetUser.setEncPassword(encPassword);
                } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
            if (Properties.REGEX_MAIL_PATTERN.matcher(newMail).find() && !newMail.equals(targetUser.getMail()))
                targetUser.setMail(newMail);

            userDAO.update(targetUser);
            return true;
        }
        return false;
    }

    public void createUser(HttpServletRequest req) {

        if (req.getParameter(NEW_USER_PARAM) != null) {
            System.out.println("NEW USER START");
            String newName = req.getParameter("name").trim();
            String newMail = req.getParameter("mail").trim();
            String newPassword = req.getParameter("password").trim();
            String newRole = req.getParameter("role").trim();
            User newUser;

            if (!("".equals(newName) && "".equals(newPassword) && "".equals(newMail))) {
                try {
                    System.out.println("__________CREATING USER________");
                    newUser = new User(newName, new Encryptor().encrypt(newPassword), newMail);
                    if (!"".equals(newRole))
                        newUser.setRole(newRole);
                    userDAO.create(newUser);
                    System.out.println("__________USER CREATED________");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }
    }

    public void setUsersParameter(@NotNull HttpServletRequest req) {
        List<User> users;
        if (req.getParameter("filtered_name") != null)
            System.out.println("filtered_name"); //mby finish
        users = new UserDAO().getAll();
        req.setAttribute("users", users);
    }

}
