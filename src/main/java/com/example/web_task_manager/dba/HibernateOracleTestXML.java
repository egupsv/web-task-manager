package com.example.web_task_manager.dba;

import com.example.web_task_manager.users.*;


public class HibernateOracleTestXML {

    public static void main(String[] args) {
            UserDAO userDAO = new UserDAO();

            User newUser = new User("test00","pa00ss");
            userDAO.insertUser(newUser);
        for (User user: userDAO.findAllUsers()) {
            System.out.println(user.getId() + " "+ user.getName() + " " + user.getEncPassword());
        }


    }

}