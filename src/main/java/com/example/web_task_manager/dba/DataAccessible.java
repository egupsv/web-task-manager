package com.example.web_task_manager.dba;


import java.util.List;

public abstract class DataAccessible<E,K> {
    public abstract List<E> getAll();
    public abstract E getEntityById(K id);
    public abstract E update(E entity);
    public abstract boolean delete(K id);
    public abstract boolean create(E entity);
}
