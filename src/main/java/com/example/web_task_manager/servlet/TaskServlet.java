package com.example.web_task_manager.servlet;

import com.example.web_task_manager.model.Task;
import com.example.web_task_manager.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TaskServlet extends AuthServletTemplate {
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private String targetUserName;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        super.doGet(request, response);
        String pathInfo = request.getPathInfo();

        targetUserName = request.getSession().getAttribute("login").toString();
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
            List<Task> tasks = taskDAO.getUserTasks(targetUser);
            request.setAttribute("tasks", tasks);
            System.out.println("CAST /index.jsp");
            getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        super.doPost(request,response);
        if (targetUserName == null)
            targetUserName = user.getName();

        boolean access = (targetUserName.equals(user.getName()) || isAdmin);
        if (access) {
            if (request.getParameter("delete") != null) {
                int deletedTaskID = Integer.parseInt(request.getParameter("delete"));
                taskDAO.delete(deletedTaskID);
            }
            if (request.getParameter("complete") != null) {
                int completedTaskID = Integer.parseInt(request.getParameter("complete"));
                Task task = taskDAO.getEntityById(completedTaskID);
                task.setCompleted(!task.isCompleted());
                taskDAO.update(task);

            }
            if (request.getParameter("name") != null) {
                String name = request.getParameter("name");
                String description = request.getParameter("description") != null ? request.getParameter("description") : "";
                String timeStr = request.getParameter("time");
                Date time = null;
                try {
                    time = formatter.parse(timeStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Task createdTask = new Task(name, description, time, userDAO.getUserByName(targetUserName));
                taskDAO.create(createdTask);
            }
            if (request.getParameter("Logout") != null) { // todo: delete or replace
                request.setAttribute("login", null);
                request.setAttribute("password", null);
                return;
            }
        }
        response.sendRedirect(request.getContextPath() + "/tasks/" + targetUserName);
    }
}
