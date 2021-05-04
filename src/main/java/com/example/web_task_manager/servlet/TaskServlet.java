package com.example.web_task_manager.servlet;

import com.example.web_task_manager.CookieName;
import com.example.web_task_manager.converter.Converter;
import com.example.web_task_manager.model.Task;
import com.example.web_task_manager.model.User;
import com.example.web_task_manager.users.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskServlet extends AuthServletTemplate {
    private static final Logger log = LoggerFactory.getLogger(TaskServlet.class);
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private static final String DELETE_PARAM = "delete";
    private static final String COMPLETE_PARAM = "complete";
    private static final String EXPORT_PARAM = "export";
    private static final String NAME_PARAM = "name";
    private static final String DESCRIPTION_PARAM = "description";
    private static final String TIME_PARAM = "time";
    private static final String LOGOUT_PARAM = "Logout";
    private String targetUserName;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        super.doGet(request, response);
        String pathInfo = request.getPathInfo();

        Object userNameParam = request.getSession().getAttribute("login");
        targetUserName = userNameParam == null ? EMPTY_VALUE : userNameParam.toString().trim();

        if (pathInfo != null)
            targetUserName = pathInfo.substring(1).trim();

        if (targetUserName.length() > 4) {
            User targetUser = userDAO.getUserByName(targetUserName);
            request.setAttribute("target_user", targetUser);
            if (targetUser == null) {
                System.out.println("target USER is not found");
                request.removeAttribute("target_user");
                return;
            }
            boolean access = Role.ADMIN.toString().equals(user.getRole()) || user.getName().equals(targetUser.getName());
            if (access) {
                List<Task> tasks = taskDAO.getUserTasks(targetUser);
                request.setAttribute("tasks", tasks);
                System.out.println("CAST /index.jsp");
                getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/tasks/" + user.getName());
            }
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        super.doPost(request, response);
        if (targetUserName == null)
            targetUserName = user.getName();

        boolean access = (targetUserName.equals(user.getName()) || Role.ADMIN.toString().equals(user.getRole()));
        if (access) {
            if (request.getParameter(DELETE_PARAM) != null) {
                int deletedTaskID = Integer.parseInt(request.getParameter(DELETE_PARAM));
                taskDAO.delete(deletedTaskID);
            }
            if (request.getParameter(COMPLETE_PARAM) != null) {
                int completedTaskID = Integer.parseInt(request.getParameter(COMPLETE_PARAM));
                Task task = taskDAO.getEntityById(completedTaskID);
                task.setCompleted(!task.getCompleted());
                taskDAO.update(task);
            }
            if (request.getParameter(EXPORT_PARAM) != null) {
                log.info("export");
                String parameter = request.getParameter(EXPORT_PARAM);
                String fileName = "tasks.xml";
                if(parameter.equals("all")) {
                    List<Task> tasks = taskDAO.getUserTasks(user);
                    log.info("tasks: {}", tasks);
                    response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");
                    response.setContentType("text/xml; name=\"fileName\"");
                    Converter.convertObjectToXml(tasks, fileName, response);
                } else {
                    int exportedTaskID = Integer.parseInt(parameter);
                    Task task = taskDAO.getEntityById(exportedTaskID);
                    //log.info(task.getName());
                    fileName = "task" + task.getId() + ".xml";
                    response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");
                    response.setContentType("text/xml; name=\"fileName\"");
                    Converter.convertObjectToXml(task, fileName, response);
                }
            }
            if (request.getParameter(NAME_PARAM) != null) {
                String name = request.getParameter(NAME_PARAM);
                String description = request.getParameter(DESCRIPTION_PARAM) != null ? request.getParameter(DESCRIPTION_PARAM) : EMPTY_VALUE;
                String timeStr = request.getParameter(TIME_PARAM);
                Date time = null;
                try {
                    time = formatter.parse(timeStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Task createdTask = new Task(name, description, time, userDAO.getUserByName(targetUserName));
                taskDAO.create(createdTask);
            }

        }
        System.out.println("PRE LOGOUT_PARAM CHECK");
        if (request.getParameter(LOGOUT_PARAM) != null) {
            System.out.println("deleting cookies--"); //todo че происходит
            cookieController.deleteCookie(response, CookieName.LOGIN);
            cookieController.deleteCookie(response, CookieName.PASSWORD);
            System.out.println("COOKIES DELETED--");
            request.setAttribute("login", null);
            request.setAttribute("password", null);
            return;
        }
        System.out.println("POST LOGOUT_PARAM CHECK");
        response.sendRedirect(request.getContextPath() + "/tasks/" + targetUserName);
    }
}
