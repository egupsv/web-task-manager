package com.example.web_task_manager.servlet;

import com.example.web_task_manager.Properties;
import com.example.web_task_manager.converter.Converter;
import com.example.web_task_manager.converter.UserForXml;
import com.example.web_task_manager.converter.UsersForXml;
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

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.DELETE;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

@MultipartConfig
public class UsersServlet extends AuthServletTemplate {
    private static final String NEW_USER_PARAM = "new_user_check";
    private static final String EXPORT_PARAM = "export";
    private static final String DELETE_PARAM = "delete";
    private static final String FILE_PARAM = "file";
    private static final String SUBMIT_EDIT_PARAM = "submit_edit_b";
    private static final String FILTERED_NAME = "filtered_name";
    private static final Logger log = LoggerFactory.getLogger(UsersServlet.class);
    private static final List<String> notMultiPartRequestParams = new ArrayList<>(Arrays.asList(NEW_USER_PARAM,
            EXPORT_PARAM, DELETE_PARAM, SUBMIT_EDIT_PARAM, "name", "password", "mail", "role", "filtered_name"));

    @EJB
    private Converter ejb;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info(notMultiPartRequestParams.toString());
        super.doGet(req, resp);
        req.getSession().setAttribute("invalid", null);
        if (!isAdmin) {
            resp.sendRedirect("http://localhost:8888/web_task_manager-1.0-SNAPSHOT/user");
            return;
        }

        setUsersParameter(req);
        getServletContext().getRequestDispatcher("/users.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getSession().setAttribute("invalidU", null);
        if (!isAdmin) {
            resp.sendRedirect(req.getContextPath() + "/user");
            return;
        }
        if (!editParamCheck(req)) {
            log.info("POST CREATE USER CHECK");
            createUser(req);
        }
        deleteParamCheck(req);
        if (req.getParameter(EXPORT_PARAM) != null) {
            export(req, resp);
        }
        if(req.getParameter(EXPORT_PARAM)==null &&
                req.getParameter(DELETE_PARAM) == null &&
                req.getParameter(NEW_USER_PARAM) == null &&
                req.getParameter(SUBMIT_EDIT_PARAM) == null &&
                req.getParameter("filtered_name") == null &&
                req.getParameter("name") == null &&
                req.getParameter("password") == null &&
                req.getParameter("mail") == null &&
                req.getParameter("role") == null) {
            if (req.getPart(FILE_PARAM) != null) {
                importFromFile(req, req.getParameter("flexRadioDefault").equals("replace"));
            }
        }
        resp.sendRedirect(req.getContextPath() + "/users");
    }

    private void deleteParamCheck(HttpServletRequest req) {
        String deleteParam = req.getParameter(DELETE_PARAM);
        int deleteId = deleteParam == null ? 0 : Integer.parseInt(deleteParam.trim());

        if (deleteId > 0) {
            User targetUser = new UserDAO().getEntityById(deleteId);
            if (!targetUser.getName().equals(req.getSession().getAttribute("login")))
                new UserDAO().delete(targetUser.getId());
        }
    }

    public boolean editParamCheck(HttpServletRequest req) {
        String editIdParam = req.getParameter(SUBMIT_EDIT_PARAM);
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
        if (req.getParameter(FILTERED_NAME) != null)
            log.info("filtered_name"); //mby finish
        users = new UserDAO().getAll();
        req.setAttribute("users", users);
    }

    public void export(HttpServletRequest req, HttpServletResponse resp) {
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
        ejb.convertObjectToXml(users, null, fileName, resp, true);
    }

    public void importFromFile(HttpServletRequest request, boolean replace) {
        try {
            Part filePart = request.getPart("file");
            Path submittedFileName = Paths.get(filePart.getSubmittedFileName());
            String fileName = submittedFileName.getFileName().toString();
            InputStream fileContent = filePart.getInputStream();
            log.info("file name: " + fileName);
            UsersForXml usersForXml = ejb.convertXmlToObject(fileContent);
            if (usersForXml == null) {
                String message = "<p><strong>invalid xml<strong></p>";
                request.getSession().setAttribute("invalidU", message);
            } else {
                try {
                    importUsers(usersForXml.getUsers(), replace);
                } catch (NullPointerException e) {
                    String message = "<p><strong>invalid xml<strong></p>";
                    request.getSession().setAttribute("invalidU", message);
                }
            }
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
    }

    public void importUsers(List<UserForXml> usersForXml, boolean replace) throws NullPointerException {
        List<User> users = new UserDAO().getAll();
        for (UserForXml userForXml : usersForXml) {
            User currentUser = findUser(userForXml.getName(), users);
            if (currentUser == null) {
                importNewUser(userForXml);
            } else {
                List<Task> tasks = userForXml.getTasks().getTasks();
                for (Task task : tasks) {
                    if(replace) {
                        taskDAO.deleteAllUserTasks(currentUser);
                    }
                    Task newTask = new Task(task.getName(), task.getDescription(), task.getTime(), task.getCompleted(), currentUser);
                    if (!currentUser.containTask(newTask)) {
                        taskDAO.create(newTask);
                    }
                }
            }
        }
    }


    public User findUser (String name, List<User> users) {
        for (User user : users) {
            if (name.equals(user.getName())) {
                return user;
            }
        }
        return null;
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

    public boolean checkParameters(HttpServletRequest req) {
        for (String param : notMultiPartRequestParams) {
            if (req.getParameter(param) != null) {
                return false;
            }
        }
        return true;
    }

//    public HashMap<String, BiConsumer<HttpServletRequest, HttpServletResponse>> chooseAction() {
//        HashMap<String, BiConsumer<HttpServletRequest, HttpServletResponse>> actions = new HashMap<>();
//        actions.put(COMPLETE_PARAM, this::complete);
//        actions.put(DELETE_PARAM, this::delete);
//        actions.put(NAME_PARAM, this::add);
//        actions.put(EXPORT_PARAM, this::export);
//        return actions;
//    }
}
