package com.example.web_task_manager.controller;


import com.example.web_task_manager.dba.DatabaseAccess;
import com.example.web_task_manager.dba.TaskDAO;
import com.example.web_task_manager.dba.UserDAO;
import com.example.web_task_manager.mail.MailSender;
import com.example.web_task_manager.model.Task;
import com.example.web_task_manager.model.User;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for parse input users and finding expired task
 */
public class NotifyWorker implements Runnable {

    private final TaskDAO taskDao = new TaskDAO();
    private final UserDAO userDAO = new UserDAO();
    private final MailSender mailSender = new MailSender();

    private boolean active = false;

    public static NotifyWorker INSTANCE;

    public NotifyWorker() {
        System.out.println("NOTIFY WORKER CREATED");

    }


    /**
     * Runs the parse Thread
     */
    @Override
    public void run() {
        if (DatabaseAccess.getSessionFactory() != null) {
            for (Task task : getExpiredTasks()) {
                releaseTask(task);
            }
        }
    }

    /**
     * Mail Notify about task.
     * and change complete state of task to -true
     *
     * @param task Specific task
     */
    public void releaseTask(Task task) {
        User taskUser = userDAO.getEntityById(task.getUserId());
        mailSender.sendMessage(taskUser.getMail(), task.getName(), task.toString());
        task.setCompleted(true);
        taskDao.update(task);
    }

    @SuppressWarnings("unchecked")
    public List<Task> getExpiredTasks() {
        List<Task> expired = new ArrayList<>();
        try (Session session = DatabaseAccess.getSessionFactory().openSession()) {
            String hql = "from Task task where task.completed = false and current_date > task.time";
            expired = session.createQuery(hql).list();
            System.out.println(expired.isEmpty() ? "expired list is empty" : expired.get(0));
            return expired;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("EXPIRED LIST IS ERROR");
        return expired;
    }
}
