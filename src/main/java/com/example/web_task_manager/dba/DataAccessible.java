package com.example.web_task_manager.dba;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.PersistenceException;
import java.util.List;

public abstract class DataAccessible<E, K> {
    private static final Logger log = LoggerFactory.getLogger(DataAccessible.class);
    public abstract List<E> getAll();

    public abstract E getEntityById(K id);

    /**
     * Updates target object @E
     *
     * @param entity target modified object
     * @return true if object was modified, false otherwise
     */
    public boolean update(E entity) {
        try (Session session = DatabaseAccess.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
            return true;
        } catch (PersistenceException pex) {
            log.error(pex.getMessage(), pex);
        }
        return false;
    }

    /**
     * Deletes object by id of this object
     *
     * @param id id of object
     * @return true if delete successful, false otherwise
     */
    public abstract boolean delete(K id);

    public abstract boolean create(E entity);
}
