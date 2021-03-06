package com.example.web_task_manager.dba;

import com.example.web_task_manager.users.*;


public class HibernateOracleTestXML {

    public static String DATE_PATTERN = "dd-MMM-yyyy HH:mm:ss";

    public static void main(String[] args) {



        TaskDAO taskDAO = new TaskDAO();

        for (User user : new UserDAO().getAll()) {
            System.out.println(user.toString());
        }
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAA_____________________AAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println(new UserDAO().getUserByName("test01").toString());
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAA_S___________________________AAAAAAAAAAAAAAAAAAAAAA/");

    }

}