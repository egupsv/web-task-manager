package com.example.web_task_manager.dba;

import com.example.web_task_manager.model.Task;
import com.example.web_task_manager.model.User;

import java.util.Date;

public class HibernateOracleTestXML {

    public static String DATE_PATTERN = "dd-MMM-yyyy HH:mm:ss";
    public static TaskDAO taskDAO = new TaskDAO();
    public static UserDAO userDAO = new UserDAO();

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            User user = new User("DefaultUser" + i, "pass" + i);
            userDAO.create(user);
            Task task = new Task(user.getName() + "task", "test", new Date(), userDAO.getUserByName(user.getName()).getId());
            taskDAO.create(task);
        }
       // System.out.println(Utils.REGEX_LOGIN_PATTERN.matcher("awsf").find());
    }


}