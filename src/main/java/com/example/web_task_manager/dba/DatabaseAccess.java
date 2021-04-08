package com.example.web_task_manager.dba;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class DatabaseAccess {
    public static DatabaseAccess INSTANCE;
    private StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;
    private static boolean isLoading = false;

    public DatabaseAccess() {
        if (isLoading)
            return;
        System.out.println("CREATING INSTANCE OF DA");
        if (sessionFactory != null) {
            closeFactory();
        }
        try {
            isLoading = true;
            registry = new StandardServiceRegistryBuilder()
                    .configure()
                    .build();


            System.out.println("creating factory-------------------------");
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            System.out.println("factory created-----------------------");
        } catch (Exception ex) {
            StandardServiceRegistryBuilder.destroy(registry);
            ex.printStackTrace();
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
