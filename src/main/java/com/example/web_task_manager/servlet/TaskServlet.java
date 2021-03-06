package com.example.web_task_manager.servlet;

import com.example.web_task_manager.tasks.Task;
import com.example.web_task_manager.tasks.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(TaskServlet.class);
    private TaskRepository tr;

    public TaskServlet() throws IOException {
    this.tr = new TaskRepository();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date time1 = null;
        Date time2 = null;
        try {
            time1 = formatter.parse("05.03.2021 16:40");
            time2 = formatter.parse("05.03.2021 16:50");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tr.addTask(new Task("meeting", "bla-bla-bla", time1, 1));
        tr.addTask(new Task("call", "bla-bla", time2, 2));
    }

    @Override public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.info("tasks");
        request.setAttribute("tasks", tr.getTasks());
        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("here");
        log.info("delete " + request.getParameter("delete"));
        log.info("complete " + request.getParameter("complete"));
        if (request.getParameter("delete") != null) {
            int deletedTaskID = Integer.parseInt(request.getParameter("delete"));
            tr.removeTask(deletedTaskID);
        }
        if (request.getParameter("complete") != null) {
            int completedTaskID = Integer.parseInt(request.getParameter("complete"));
            tr.changeTaskState(completedTaskID);
        }
        if (request.getParameter("name") != null) {
            String name = request.getParameter("name");
            String description = request.getParameter("description") != null ? request.getParameter("description") : "";
            String timeStr = request.getParameter("time");
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            Date time = null;
            try {
                time = formatter.parse(timeStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            tr.addTask(new Task(name, description, time, tr.calculateMaxId() + 1));
        }
        request.setAttribute("tasks", tr.getTasks());
        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
