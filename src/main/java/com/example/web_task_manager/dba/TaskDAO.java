package com.example.web_task_manager.dba;

import com.example.web_task_manager.model.Task;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO extends DataAccessible<Task, Integer> {
    private static final Logger log = LoggerFactory.getLogger(TaskDAO.class);

    @Override
    public boolean create(Task task) {
        try (Session session = DatabaseAccess.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(task);
            transaction.commit();
            return true;
        } catch (HibernateException ex) {

            ex.printStackTrace();
        }
        return false;
    }


    @Override
    public Task getEntityById(Integer id) {
        try (Session session = DatabaseAccess.getSessionFactory().openSession()) {
            return session.get(Task.class, id);
        } catch (PersistenceException pex) {
            pex.printStackTrace();
        }
        return null;
    }

    /**
     * Deletes Task
     *
     * @param id id of task
     * @return true - if successful delete, false - otherwise
     */
    @Override
    public boolean delete(Integer id) {
        try (Session session = DatabaseAccess.getSessionFactory().openSession()) {
            Task taskInstance = session.load(Task.class, id);
            if (taskInstance != null) {
                session.getTransaction().begin();
                session.delete(taskInstance);
                session.getTransaction().commit();
                return true;
            }

        } catch (PersistenceException pex) {
            pex.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes All @task of user
     *
     * @param userId id of user
     */
    public void deleteAllUserTasks(int userId) {
        for (Task task : getUserTasks(userId)) {
            delete(task.getId());
        }

    }

    /**
     * @param userId id of user
     * @return list uf user tasks
     */
    public List<Task> getUserTasks(int userId) {
        try (Session session = DatabaseAccess.getSessionFactory().openSession()) {
            CriteriaQuery<Task> c = session.getCriteriaBuilder().createQuery(Task.class);
            Root<Task> from = c.from(Task.class);
            c.select(from);
            c.where(session.getCriteriaBuilder().equal(from.get("userId"), userId));
            return session.createQuery(c).getResultList();
        } catch (NoResultException ignored) {
            log.info("_______________________________task NO RESULTS FOR ID = " + userId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        log.info("_______________________________task NO RESULTS FOR ID = " + userId);
        return null;
    }

    @Override
    public List<Task> getAll() {
        List<Task> taskList = new ArrayList<>();

        try (Session session = DatabaseAccess.getSessionFactory().openSession()) {
            taskList = session.createQuery("from Task").list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return taskList;
    }

}
