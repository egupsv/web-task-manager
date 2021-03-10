package com.example.web_task_manager.dba;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class DatabaseAccess {
    public static DatabaseAccess INSTANCE;
    private StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public DatabaseAccess() {
        System.out.println("CREATING INSTANCE OF DA");
        if(sessionFactory != null)
        {
            closeFactory();
        }
        try {
            registry = new StandardServiceRegistryBuilder()
                    .configure()
                    .build();


            System.out.println("creating factory-------------------------");
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            System.out.println("factory created-----------------------");
        }
        catch (Exception ex){
            StandardServiceRegistryBuilder.destroy(registry);
            ex.printStackTrace();
        }
    }

    public static SessionFactory getSessionFactory() {
        if(INSTANCE == null)
             INSTANCE = new DatabaseAccess();
        return sessionFactory;
    }

    public static void closeFactory(){
        sessionFactory.close();
    }

}
