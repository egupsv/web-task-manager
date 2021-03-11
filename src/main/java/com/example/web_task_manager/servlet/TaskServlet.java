package com.example.web_task_manager.servlet;

import com.example.web_task_manager.dba.TaskDAO;
import com.example.web_task_manager.dba.UserDAO;
import com.example.web_task_manager.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TaskServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(TaskServlet.class);
    private final TaskDAO taskDAO;
    private final UserDAO userDAO;
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public TaskServlet() {
        this.taskDAO = new TaskDAO();
        this.userDAO = new UserDAO();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.info("tasks");
        log.info("log out " + request.getParameter("Logout"));
        if (request.getParameter("Logout") != null) {
            final HttpSession session = request.getSession();
            session.removeAttribute("password");
            session.removeAttribute("login");
            getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            List<Task> tasks = taskDAO.getUserTasks(userDAO.getUserByName(request.getSession().getAttribute("login").toString()));
            request.setAttribute("tasks", tasks);
            getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getSession().getAttribute("login").toString();
        int userId = userDAO.getUserByName(login).getId();
        log.info("here");
        log.info("delete " + request.getParameter("delete"));
        log.info("complete " + request.getParameter("complete"));
        log.info("log out " + request.getParameter("Log out"));

        if (request.getParameter("delete") != null) {
            int deletedTaskID = Integer.parseInt(request.getParameter("delete"));
            if (taskDAO.taskBelongs(deletedTaskID, userId))
                taskDAO.delete(deletedTaskID);

        }
        if (request.getParameter("complete") != null) {
            int completedTaskID = Integer.parseInt(request.getParameter("complete"));
            if (taskDAO.taskBelongs(completedTaskID, userId)) {
                Task task = taskDAO.getEntityById(completedTaskID);
                task.setCompleted(!task.isCompleted());

                taskDAO.update(task);
            }
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
            Task createdTask = new Task(name, description, time, userDAO.getUserByName(login));
            taskDAO.create(createdTask);
        }
        if (request.getParameter("Logout") != null) {
            request.setAttribute("login", null);
            request.setAttribute("password", null);
        }
        List<Task> tasks = taskDAO.getUserTasks(userDAO.getUserByName(request.getSession().getAttribute("login").toString()));
        request.setAttribute("tasks", tasks);

        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
