package com.example.web_task_manager.servlet;

import com.example.web_task_manager.Utils;
import com.example.web_task_manager.converter.Converter;
import com.example.web_task_manager.converter.TasksForXml;
import com.example.web_task_manager.converter.UserForXml;
import com.example.web_task_manager.converter.UsersForXml;
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
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@MultipartConfig
public class TaskServlet extends AuthServletTemplate {
    private static final Logger log = LoggerFactory.getLogger(TaskServlet.class);
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private static final String DELETE_PARAM = "delete";
    private static final String COMPLETE_PARAM = "complete";
    private static final String EXPORT_PARAM = "export";
    private static final String FILE_PARAM = "file";
    private static final String NAME_PARAM = "name";
    private static final String DESCRIPTION_PARAM = "description";
    private static final String TIME_PARAM = "time";
    private static final String TARGET_USER_PARAM = "target_user";
    private static final String LOGOUT_PARAM = "Logout";
    private String targetUserName;

    @EJB
    private Converter ejb;

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
            request.setAttribute(TARGET_USER_PARAM, targetUser);
            if (targetUser == null) {
                System.out.println("target USER not found");
                request.removeAttribute("target_user");
                response.sendRedirect(request.getContextPath() + "/tasks/" + user.getName());
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
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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
                Task exportedTask = null;
                List<User> users = new ArrayList<>();
                users.add(user);
                int exportedTaskID = 0;
                if(!parameter.equals("all")) {
                    exportedTaskID = Integer.parseInt(parameter);
                    exportedTask = taskDAO.getEntityById(exportedTaskID);
                    fileName = "task" + exportedTaskID + ".xml";
                }
                response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");
                response.setContentType("text/xml; name=\"fileName\"");
                ejb.convertObjectToXml(users, exportedTask, fileName, response);
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
            if(request.getParameter(NAME_PARAM) == null &&
                    request.getParameter(EXPORT_PARAM) == null &&
                    request.getParameter(DELETE_PARAM) == null &&
                    request.getParameter(COMPLETE_PARAM) == null) {
                if (request.getPart("file") != null) {
                    log.info("import");
                    try {
                        Part filePart = request.getPart("file");
                        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                        InputStream fileContent = filePart.getInputStream();
                        log.info("file name: " + fileName);
                        UsersForXml usersForXml = ejb.convertXmlToObject(fileContent);
                        List<UserForXml> users = usersForXml.getUsers();
                        StringBuilder existedTasks = new StringBuilder();
                        //List<Task> existedTasks = new ArrayList<>();
                        for (UserForXml userForXml : users) {
                            if (userForXml.getName().equals(user.getName())) {
                                List<Task> tasks = userForXml.getTasks().getTasks();
                                for (Task task : tasks) {
                                    Task newTask = new Task(task.getName(), task.getDescription(), task.getTime(), task.getCompleted(), user);
                                    if (!user.containTask(newTask)) {
                                        log.info("not contain");
                                        taskDAO.create(newTask);
                                    } else {
                                        existedTasks.append("\nname: ").append(newTask.getName()).
                                                append("\ndescription: ").append(newTask.getDescription()).
                                                append("\ntime: ").append(Utils.getFormattedTime(newTask.getTime())).
                                                append("\n");
                                    }
                                }
                            }
                        }
                        if (!"".equals(existedTasks.toString())) {
                            String message = "Tasks:" + existedTasks + "already exists";
                            request.setAttribute("existed", message);
                        }
                    } catch (IOException | ServletException | JAXBException e) {
                        e.printStackTrace();
                    }
                }

//                String description = request.getParameter(DESCRIPTION_PARAM) != null ? request.getParameter(DESCRIPTION_PARAM) : EMPTY_VALUE;
//                String timeStr = request.getParameter(TIME_PARAM);
//                Date time = null;
//                try {
//                    time = formatter.parse(timeStr);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                Task createdTask = new Task(name, description, time, userDAO.getUserByName(targetUserName));
//                taskDAO.create(createdTask);
            }

        }

        response.sendRedirect(request.getContextPath() + "/tasks/" + targetUserName);
    }
}
