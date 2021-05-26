package com.example.web_task_manager.servlet;

import com.example.web_task_manager.Properties;
import com.example.web_task_manager.constants.Constants;
import com.example.web_task_manager.converter.Converter;
import com.example.web_task_manager.converter.UserForXml;
import com.example.web_task_manager.converter.UsersForXml;
import com.example.web_task_manager.dba.UserDAO;
import com.example.web_task_manager.exceptions.ExportException;
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

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

@MultipartConfig
public class UsersServlet extends AuthServletTemplate {
    private static final Logger log = LoggerFactory.getLogger(UsersServlet.class);

    @EJB
    private Converter ejb;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        req.getSession().setAttribute(Constants.INVALID_ATTRIBUTE, null);
        req.getSession().setAttribute(Constants.EXISTED_ATTRIBUTE, null);
        req.getSession().setAttribute(Constants.EXPORT_FAIL, null);
        if (!isAdmin) {
            resp.sendRedirect(req.getContextPath() + "/user");
            return;
        }

        setUsersParameter(req);
        getServletContext().getRequestDispatcher("/users.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getSession().setAttribute(Constants.INVALID_U_ATTRIBUTE, null);
        if (!isAdmin) {
            resp.sendRedirect(req.getContextPath() + "/user");
            return;
        }
        if(req.getParameter(Constants.SUBMIT_EDIT_B) != null) {
            editParamCheck(req, resp);
        }
        if(req.getParameter(Constants.EXPORT_PARAM) != null) {
            export(req, resp);
        }
        if(req.getParameter(Constants.DELETE_PARAM) != null) {
            deleteParamCheck(req, resp);
        }
        if(isMultipartRequest(req)) {
            if (req.getPart(Constants.FILE_PARAM) != null) {
                importFromFile(req, Constants.REPLACE_PARAM.equals(req.getParameter("flexRadioDefault")));
            }
        }
//        } else {
//            try {
//                chooseAction().get(req.getParameterNames().nextElement()).accept(req, resp);
//            } catch (NullPointerException e) {
//                resp.sendRedirect(req.getContextPath() + "/users");
//            }
//        }
        resp.sendRedirect(req.getContextPath() + "/users");
    }

    public boolean isMultipartRequest (HttpServletRequest req) {
        return (req.getParameter(Constants.EXPORT_PARAM) == null &&
                req.getParameter(Constants.NEW_USER_PARAM) == null &&
                req.getParameter(Constants.SUBMIT_EDIT_B) == null &&
                req.getParameter(Constants.DELETE_PARAM) == null);
    }

    private void deleteParamCheck(HttpServletRequest req, HttpServletResponse resp) {
        String deleteParam = req.getParameter(Constants.DELETE_PARAM);
        int deleteId = Integer.parseInt(deleteParam.trim());
        User targetUser = new UserDAO().getEntityById(deleteId);
        if (!targetUser.getName().equals(req.getSession().getAttribute(Constants.LOGIN_ATTRIBUTE))) {
            new UserDAO().delete(targetUser.getId());
        }
    }

    public void editParamCheck(HttpServletRequest req, HttpServletResponse resp) {
        String editIdParam = req.getParameter(Constants.SUBMIT_EDIT_B);
        int editableId = Integer.parseInt(editIdParam.trim());
        if (editableId > 0) {
            User targetUser = new UserDAO().getEntityById(editableId);
            String newName = req.getParameter("name").trim();
            String newMail = req.getParameter("mail").trim();
            String newPassword = req.getParameter("password").trim();
            String newRole = req.getParameter("role").trim();

            if (newRole.length() > 0 && !targetUser.getName().equals(req.getSession().getAttribute(Constants.LOGIN_ATTRIBUTE)))
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
        } else {
            log.info("POST CREATE USER CHECK");
            createUser(req);
        }
    }

    public void createUser(HttpServletRequest req) {

        if (req.getParameter(Constants.NEW_USER_PARAM) != null) {
            log.info("NEW USER START");
            String newName = req.getParameter("name").trim();
            String newMail = req.getParameter("mail").trim();
            String newPassword = req.getParameter("password").trim();
            String newRole = req.getParameter("role").trim();
            User newUser;

            if (!("".equals(newName) && "".equals(newPassword) && "".equals(newMail))) {
                try {
                    log.info("__________CREATING USER________");
                    newUser = new User(newName, new Encryptor().encrypt(newPassword), newMail);
                    if (!"".equals(newRole))
                        newUser.setRole(newRole);
                    userDAO.create(newUser);
                    log.info("__________USER CREATED________");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }
    }

    public void setUsersParameter(@NotNull HttpServletRequest req) {
        List<User> users;
        users = new UserDAO().getAll();
        req.setAttribute(Constants.USERS_ATTRIBUTE, users);
    }

    public void export(HttpServletRequest req, HttpServletResponse resp) {
        String parameter = req.getParameter(Constants.EXPORT_PARAM);
        String fileName = "users.xml";
        List<User> users = new ArrayList<>();
        if(Constants.ALL_PARAM.equals(parameter)) {
            users = userDAO.getAll();
        } else {
            int exportedUserID = Integer.parseInt(parameter);
            User user = userDAO.getEntityById(exportedUserID);
            users.add(user);
            fileName = "user" + user.getId() + ".xml";
        }
        resp.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");
        resp.setContentType("text/xml; name=\"fileName\"");
        try {
            ejb.convertObjectToXml(users, null, fileName, resp, true);
        } catch (ExportException e) {
            log.error("cause of ExportException: " + e.getCause());
            req.getSession().setAttribute(Constants.EXPORT_FAIL, e.getMessage());
        }
    }

    public void importFromFile(HttpServletRequest request, boolean replace) {
        try {
            Part filePart = request.getPart(Constants.FILE_PARAM);
            Path submittedFileName = Paths.get(filePart.getSubmittedFileName());
            String fileName = submittedFileName.getFileName().toString();
            InputStream fileContent = filePart.getInputStream();
            log.info("file name: " + fileName);
            UsersForXml usersForXml = ejb.convertXmlToObject(fileContent);
            if (usersForXml == null) {
                String message = "invalid xml";
                request.getSession().setAttribute(Constants.INVALID_U_ATTRIBUTE, message);
            } else {
                try {
                    importUsers(usersForXml.getUsers(), replace);
                } catch (NullPointerException e) {
                    String message = "invalid xml";
                    request.getSession().setAttribute(Constants.INVALID_U_ATTRIBUTE, message);
                }
            }
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
    }

    public void importUsers(List<UserForXml> usersForXml, boolean replace) throws NullPointerException {
        for (UserForXml userForXml : usersForXml) {
            User currentUser = userDAO.getUserByName(userForXml.getName());
            if (currentUser == null) {
                importNewUser(userForXml);
            } else {
                List<Task> tasks = userForXml.getTasks().getTasks();
                for (Task task : tasks) {
                    if(replace) {
                        taskDAO.deleteAllUserTasks(currentUser);
                    }
                    Task newTask = new Task(task.getName(), task.getDescription(), task.getTime(), task.getCompleted(), currentUser);
                    if (!currentUser.containsTask(newTask)) {
                        taskDAO.create(newTask);
                    }
                }
            }
        }
    }

    public void importNewUser (UserForXml userForXml) {
        if (userForXml.getMail() == null || userForXml.getEncPassword() == null) {
            throw new NullPointerException();
        } else {
            User newUser = new User(userForXml.getName(),
                    userForXml.getEncPassword(),
                    userForXml.getMail());
            userDAO.create(newUser);
            List<Task> tasks = userForXml.getTasks().getTasks();
            for (Task task : tasks) {
                Task newTask = new Task(task.getName(), task.getDescription(), task.getTime(), task.getCompleted(), user);
                taskDAO.create(newTask);
            }
        }
    }

    public HashMap<String, BiConsumer<HttpServletRequest, HttpServletResponse>> chooseAction() {
        HashMap<String, BiConsumer<HttpServletRequest, HttpServletResponse>> actions = new HashMap<>();
        actions.put(Constants.SUBMIT_EDIT_B, this::editParamCheck);
        actions.put(Constants.DELETE_PARAM, this::deleteParamCheck);
        actions.put(Constants.EXPORT_PARAM, this::export);
        return actions;
    }
}