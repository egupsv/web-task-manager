package com.example.web_task_manager.controller;


import com.example.web_task_manager.dba.DatabaseAccess;
import com.example.web_task_manager.dba.TaskDAO;
import com.example.web_task_manager.dba.UserDAO;
import com.example.web_task_manager.mail.MailSender;
import com.example.web_task_manager.model.Task;
import com.example.web_task_manager.model.User;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for parse input users and finding expired task
 */
public class NotifyWorker implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(NotifyWorker.class);
    private final TaskDAO taskDao = new TaskDAO();
    private final UserDAO userDAO = new UserDAO();
    private final MailSender mailSender = new MailSender();

    public NotifyWorker() {
        log.info("NOTIFY WORKER CREATED");

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
            log.info(expired.isEmpty() ? "expired list is empty" : expired.get(0).toString());
            return expired;
        } catch (Exception ex) {
            log.error("EXPIRED LIST IS ERROR");
            log.error(ex.getMessage(), ex);
        }

        return expired;
    }
}
