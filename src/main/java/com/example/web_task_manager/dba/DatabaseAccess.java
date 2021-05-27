package com.example.web_task_manager.dba;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseAccess {
    private static final Logger log = LoggerFactory.getLogger(DatabaseAccess.class);
    public static DatabaseAccess INSTANCE;
    private StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;
    private static boolean isLoading = false;

    public DatabaseAccess() {
        if (isLoading)
            return;
        log.info("CREATING INSTANCE OF DA");
        if (sessionFactory != null) {
            closeFactory();
        }
        try {
            isLoading = true;
            registry = new StandardServiceRegistryBuilder()
                    .configure()
                    .build();


            log.info("creating factory-------------------------");
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            log.info("factory created-----------------------");
        } catch (Exception ex) {
            StandardServiceRegistryBuilder.destroy(registry);
            log.error(ex.getMessage());
            log.error("cause: " + ex.getCause());
            sessionFactory = null;
        }
        isLoading = false;
    }

    public static SessionFactory getSessionFactory() {
        if (INSTANCE == null || sessionFactory == null)
            INSTANCE = new DatabaseAccess();
        return sessionFactory;
    }

    public static void closeFactory() {
        sessionFactory.close();
    }

}
