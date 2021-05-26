package com.example.web_task_manager.servlet;

import com.example.web_task_manager.constants.Constants;
import com.example.web_task_manager.converter.Converter;
import com.example.web_task_manager.converter.UserForXml;
import com.example.web_task_manager.converter.UsersForXml;
import com.example.web_task_manager.exceptions.ExportException;
import com.example.web_task_manager.model.Task;
import com.example.web_task_manager.model.User;
import com.example.web_task_manager.servlet.template.AuthServletTemplate;
import com.example.web_task_manager.users.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;


@MultipartConfig
public class TaskServlet extends AuthServletTemplate {
    private static final Logger log = LoggerFactory.getLogger(TaskServlet.class);
    private String targetUserName;

    @EJB
    private Converter ejb;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        super.doGet(request, response);
        request.getSession().setAttribute(Constants.INVALID_U_ATTRIBUTE, null);
        request.getSession().setAttribute(Constants.EXPORT_FAIL, null);
        String pathInfo = request.getPathInfo();

        Object userNameParam = request.getSession().getAttribute("login");
        targetUserName = userNameParam == null ? Constants.EMPTY_VALUE : userNameParam.toString().trim();

        if (pathInfo != null)
            targetUserName = pathInfo.substring(1).trim();

        if (targetUserName.length() > User.MIN_USER_NAME) {
            User targetUser = userDAO.getUserByName(targetUserName);
            request.setAttribute(Constants.TARGET_USER_PARAM, targetUser);
            if (targetUser == null) {
                log.info("target USER not found");
                request.removeAttribute("target_user");
                response.sendRedirect(request.getContextPath() + "/tasks/" + user.getName());
                return;
            }
            boolean access = Role.ADMIN.toString().equals(user.getRole()) || user.getName().equals(targetUser.getName());
            if (access) {
                List<Task> tasks = taskDAO.getUserTasks(targetUser);
                request.setAttribute("tasks", tasks);
                log.info("CAST /index.jsp");
                getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/tasks/" + user.getName());
            }
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        super.doPost(request, response);
        request.getSession().setAttribute(Constants.EXISTED_ATTRIBUTE, null);
        request.getSession().setAttribute(Constants.INVALID_ATTRIBUTE, null);
        request.getSession().setAttribute(Constants.EXPORT_FAIL, null);
        if (targetUserName == null)
            targetUserName = user.getName();

        boolean access = (targetUserName.equals(user.getName()) || Role.ADMIN.toString().equals(user.getRole()));
        if (access) {
            if(isMultipartRequest(request)) {
                if (request.getPart(Constants.FILE_PARAM) != null) {
                    importFromFile(request);
                }
            } else {
                chooseAction().get(request.getParameterNames().nextElement()).accept(request, response);
            }
        }
        response.sendRedirect(request.getContextPath() + "/tasks/" + targetUserName);
    }

    public boolean isMultipartRequest (HttpServletRequest request) {
        return (request.getParameter(Constants.NAME_PARAM) == null &&
                request.getParameter(Constants.EXPORT_PARAM) == null &&
                request.getParameter(Constants.DELETE_PARAM) == null &&
                request.getParameter(Constants.COMPLETE_PARAM) == null);
    }

    public void export(HttpServletRequest request, HttpServletResponse response) {
        log.info("export");
        String parameter = request.getParameter(Constants.EXPORT_PARAM);
        String fileName = "tasks.xml";
        Task exportedTask = null;
        List<User> users = new ArrayList<>();
        users.add(user);
        int exportedTaskID = 0;
        if(!Constants.ALL_PARAM.equals(parameter)) {
            exportedTaskID = Integer.parseInt(parameter);
            exportedTask = taskDAO.getEntityById(exportedTaskID);
            fileName = "task" + exportedTaskID + ".xml";
        }
        response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");
        response.setContentType("text/xml; name=\"fileName\"");
        try {
            ejb.convertObjectToXml(users, exportedTask, fileName, response, false);
        } catch (ExportException e) {
            log.error("cause of ExportException: " + e.getCause());
            request.getSession().setAttribute(Constants.EXPORT_FAIL, e.getMessage());
        }

    }

    public void complete(HttpServletRequest request, HttpServletResponse response) {
        int completedTaskID = Integer.parseInt(request.getParameter(Constants.COMPLETE_PARAM));
        Task task = taskDAO.getEntityById(completedTaskID);
        task.setCompleted(!task.getCompleted());
        taskDAO.update(task);
    }

    public void delete(HttpServletRequest request, HttpServletResponse response) {
        int deletedTaskID = Integer.parseInt(request.getParameter(Constants.DELETE_PARAM));
        taskDAO.delete(deletedTaskID);
    }

    public void add(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter(Constants.NAME_PARAM);
        String description = request.getParameter(Constants.DESCRIPTION_PARAM) != null ?
                request.getParameter(Constants.DESCRIPTION_PARAM) : Constants.EMPTY_VALUE;
        String timeStr = request.getParameter(Constants.TIME_PARAM);
        Date time = null;
        try {
            time = Constants.formatter.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Task createdTask = new Task(name, description, time, userDAO.getUserByName(targetUserName));
        taskDAO.create(createdTask);
    }

    public void importFromFile(HttpServletRequest request) {
        try {
            Part filePart = request.getPart("file");
            Path submittedFileName = Paths.get(filePart.getSubmittedFileName());
            String fileName = submittedFileName.getFileName().toString();
            InputStream fileContent = filePart.getInputStream();
            log.info("file name: " + fileName);
            UsersForXml usersForXml = ejb.convertXmlToObject(fileContent);
            if (usersForXml == null) {
                String message = "invalid xml";
                request.getSession().setAttribute(Constants.INVALID_ATTRIBUTE, message);
            } else {
                List<UserForXml> users = usersForXml.getUsers();
                List<Task> existedTasks = new ArrayList<>();
                //StringBuilder existedTasks = new StringBuilder();
                importTasks(users, existedTasks);
                if (!existedTasks.isEmpty()) {
                    request.getSession().setAttribute(Constants.EXISTED_ATTRIBUTE, existedTasks);
                }
            }
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
    }

    public void importTasks(List<UserForXml> users, List<Task> existedTasks) {
        for (UserForXml userForXml : users) {
            if (!userForXml.getName().equals(user.getName())) {
                continue;
            }
            List<Task> tasks = userForXml.getTasks().getTasks();
            for (Task task : tasks) {
                Task newTask = new Task(task.getName(), task.getDescription(), task.getTime(), task.getCompleted(), user);
                if (!user.containsTask(newTask)) {
                    log.info("not contain");
                    taskDAO.create(newTask);
                } else {
                    existedTasks.add(newTask);
                }
            }
        }
    }

    public HashMap<String, BiConsumer<HttpServletRequest, HttpServletResponse>> chooseAction() {
        HashMap<String, BiConsumer<HttpServletRequest, HttpServletResponse>> actions = new HashMap<>();
        actions.put(Constants.COMPLETE_PARAM, this::complete);
        actions.put(Constants.DELETE_PARAM, this::delete);
        actions.put(Constants.NAME_PARAM, this::add);
        actions.put(Constants.EXPORT_PARAM, this::export);
        return actions;
   }
}
