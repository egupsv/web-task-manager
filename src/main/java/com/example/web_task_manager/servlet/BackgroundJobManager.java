package com.example.web_task_manager.servlet;

import com.example.web_task_manager.controller.NotifyWorker;
import com.example.web_task_manager.dba.DatabaseAccess;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class BackgroundJobManager implements ServletContextListener {
    private ScheduledExecutorService scheduler;
    public static boolean DB_INIT = false;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("______________________________________________________________________");
        System.out.println("V = 12");
        System.out.println("______________________________________________________________________");
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new NotifyWorker(), 1000, 10000, TimeUnit.MILLISECONDS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }

}

