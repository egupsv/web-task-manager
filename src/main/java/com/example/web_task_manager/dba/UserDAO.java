package com.example.web_task_manager.dba;


import com.example.web_task_manager.users.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;


public class UserDAO {

    public UserDAO() {


    }

    public void insertUser(User user) {
        try (Session session = DatabaseAccess.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(user);

            transaction.commit();
        } catch (HibernateException ex) {

            ex.printStackTrace();
        }
    }

    public List<User> findAllUsers() {
        List<User> userlist = new ArrayList<User>();

        try (Session session = DatabaseAccess.getSessionFactory().openSession()) {
            userlist = session.createQuery("from User").list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return userlist;
    }

}
