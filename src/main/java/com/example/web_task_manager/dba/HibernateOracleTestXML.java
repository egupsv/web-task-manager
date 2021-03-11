package com.example.web_task_manager.dba;


import com.example.web_task_manager.Properties;
import com.example.web_task_manager.mail.MailSender;


public class HibernateOracleTestXML {

    public static String DATE_PATTERN = "dd-MMM-yyyy HH:mm:ss";
    public static TaskDAO taskDAO = new TaskDAO();
    public static UserDAO userDAO = new UserDAO();

    public static void main(String[] args) throws Exception {

//        for (int i = 0; i < 10; i++) {
//            User user = new User("DefaultUser" + i, "pass" + i);
//            userDAO.create(user);
//            Task task = new Task(user.getName() + "task", "test", new Date(), userDAO.getUserByName(user.getName()).getId());
//            taskDAO.create(task);
//        }
        MailSender ms = new MailSender();
        ms.sendMessage(Properties.DEFAULT_MAIL_IN, "wqefqwef", "qewfweqdf");

    }


}