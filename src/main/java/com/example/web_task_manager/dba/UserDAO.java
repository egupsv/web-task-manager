package com.example.web_task_manager.dba;


import com.example.web_task_manager.users.User;
import org.hibernate.Session;
import org.hibernate.Transaction;


import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


public class UserDAO extends DataAccessible<User, Integer> {


    public User getUserByName(String userName) {
        try (Session session = DatabaseAccess.getSessionFactory().openSession()) {
            CriteriaQuery<User> cq = session.getCriteriaBuilder().createQuery(User.class);
            Root<User> from = cq.from(User.class);
            cq.select(from);
            cq.where(session.getCriteriaBuilder().equal(from.get("name"), userName));
            return session.createQuery(cq).getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        List<User> userlist = new ArrayList<>();

        try (Session session = DatabaseAccess.getSessionFactory().openSession()) {
            userlist = session.createQuery("from User").list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return userlist;
    }

    @Override
    public User getEntityById(Integer id) {
        User user = null;

        try (Session session = DatabaseAccess.getSessionFactory().openSession()) {
            user = session.get(User.class, id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;
    }

    @Override
    public User update(User entity) { // TODO: 06.03.2021  
        return null;
    }

    @Override
    public boolean delete(Integer id) { // TODO: 06.03.2021
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

            ex.printStackTrace();
        }
        return false;
    }
}
