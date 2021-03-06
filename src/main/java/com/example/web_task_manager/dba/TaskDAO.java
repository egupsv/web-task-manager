package com.example.web_task_manager.dba;

import com.example.web_task_manager.tasks.Task;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO extends DataAccessible<Task, Integer> {

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

    @Override
    public Task update(Task task) { // TODO: 06.03.2021
        return null;
    }

    @Override
    public boolean delete(Integer id) { // TODO: 06.03.2021
        return false;
    }

    public List<Task> getUserTasks(int userId) {
        try (Session session = DatabaseAccess.getSessionFactory().openSession()) {
            CriteriaQuery<Task> c = session.getCriteriaBuilder().createQuery(Task.class);
            Root<Task> from = c.from(Task.class);
            c.select(from);
            c.where(session.getCriteriaBuilder().equal(from.get("userId"), userId));
            return session.createQuery(c).getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
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
