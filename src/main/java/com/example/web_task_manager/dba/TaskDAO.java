package com.example.web_task_manager.dba;

import com.example.web_task_manager.model.Task;
import com.example.web_task_manager.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
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
            log.error("cause of HibernateException: ");
        }
        return false;
    }


    @Override
    public Task getEntityById(Integer id) {
        try (Session session = DatabaseAccess.getSessionFactory().openSession()) {
            return session.get(Task.class, id);
        } catch (PersistenceException pex) {
            log.error(pex.getMessage(), pex);
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
            log.error(pex.getMessage(), pex);
        }
        return false;
    }

    /**
     * Deletes All @task of user
     *
     * @param user - instance of user
     */
    public void deleteAllUserTasks(User user) {
        for (Task task : getUserTasks(user)) {
            delete(task.getId());
        }
    }

    /**
     * @param user user instance
     * @return list uf user tasks
     */
    public List<Task> getUserTasks(User user) {
        List<Task> tasksList;
        try (Session session = DatabaseAccess.getSessionFactory().openSession()) {
            CriteriaQuery<Task> c = session.getCriteriaBuilder().createQuery(Task.class);
            Root<Task> from = c.from(Task.class);
            c.select(from);
            c.where(session.getCriteriaBuilder().equal(from.get("user"), user));
            tasksList = session.createQuery(c).getResultList();
            return tasksList;
        } catch (NoResultException ignored) {

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return Collections.emptyList();
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Task> getAll() {
        List<Task> taskList = new ArrayList<>();

        try (Session session = DatabaseAccess.getSessionFactory().openSession()) {
            taskList = session.createQuery("from Task").list();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return taskList;
    }

    public boolean taskBelongs(int taskId, int userId) {
        try (Session session = DatabaseAccess.getSessionFactory().openSession()) {
            String hql = "from Task task where task.user.id = :userId and task.id = :taskId";
            Query<?> query = session.createQuery(hql);
            query.setParameter("userId", userId);
            query.setParameter("taskId", taskId);
            if (query.getSingleResult() != null)
                return true;

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return false;
    }


}
