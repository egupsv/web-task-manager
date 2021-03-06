package com.example.web_task_manager.dba;

import com.example.web_task_manager.tasks.Task;
import com.example.web_task_manager.users.*;

import java.util.Date;


public class HibernateOracleTestXML {

    public static String DATE_PATTERN = "dd-MMM-yyyy HH:mm:ss";

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new UserDAO().create(new User("name" + i, "pass"));
            new TaskDAO().create(new Task("name" + i, "description" + i, new Date(), i+1));
        }

    }


}