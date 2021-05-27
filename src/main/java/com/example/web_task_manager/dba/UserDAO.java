package com.example.web_task_manager.dba;


import com.example.web_task_manager.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;


public class UserDAO extends DataAccessible<User, Integer> {
    private static final Logger log = LoggerFactory.getLogger(DataAccessible.class);

    public User getUserByName(String userName) {
        try (Session session = DatabaseAccess.getSessionFactory().openSession()) {
            CriteriaQuery<User> cq = session.getCriteriaBuilder().createQuery(User.class);
            Root<User> from = cq.from(User.class);
            cq.select(from);
            cq.where(session.getCriteriaBuilder().equal(from.get("name"), userName));

            return session.createQuery(cq).getSingleResult();
        } catch (NoResultException ignored) {

        } catch (PersistenceException ex) {
            log.error("cause of PersistenceException " + ex.getCause());
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAll() {
        List<User> userlist;
        try (Session session = DatabaseAccess.getSessionFactory().openSession()) {
            userlist = session.createQuery("from User").list();
            return userlist;
        } catch (NoResultException ignored) {

        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.error("cause: " + ex.getCause());
        }
        return Collections.emptyList();
    }

    @Override
    public User getEntityById(Integer id) {
        try (Session session = DatabaseAccess.getSessionFactory().openSession()) {
            return session.get(User.class, id);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.error("cause: " + ex.getCause());
        }
        return null;
    }


    @Override
    public boolean delete(Integer id) {
        try (Session session = DatabaseAccess.getSessionFactory().openSession()) {
            User userInstance = session.load(User.class, id);
            if (userInstance != null) {
                new TaskDAO().deleteAllUserTasks(userInstance);
                session.getTransaction().begin();
                session.delete(userInstance);
                session.getTransaction().commit();
                return true;
            }
        } catch (PersistenceException pex) {
            log.error("cause of PersistenceException: " + pex.getCause());
        }
        return false;
    }

    @Override
    public boolean create(User user) {
        try (Session session = DatabaseAccess.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(user); //session.persist(user);

            transaction.commit();
            return true;
        } catch (PersistenceException ex) {
            log.error("cause of PersistenceException: " + ex.getCause());
        }
        return false;
    }

    public User getUserByMail(String userMail) {
        try (Session session = DatabaseAccess.getSessionFactory().openSession()) {
            CriteriaQuery<User> cq = session.getCriteriaBuilder().createQuery(User.class);
            Root<User> from = cq.from(User.class);
            cq.select(from);
            cq.where(session.getCriteriaBuilder().equal(from.get("mail"), userMail));

            return session.createQuery(cq).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (PersistenceException ex) {
            log.error("cause of PersistenceException: " + ex.getCause());
        }
        return null;
    }
}
