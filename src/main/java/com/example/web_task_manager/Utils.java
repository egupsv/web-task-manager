package com.example.web_task_manager;

import com.example.web_task_manager.model.Task;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Utils {

    public static String getFormattedTime(Task task) {
        return getFormattedTime(task.getTime());
    }

    public static String getFormattedTime(Date date) {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm").format(date);
    }

    public static String getState(Task task) {
        return task.getCompleted() ? "complete" : "incomplete";
    }

    public static String getCurrentDateString() {
        return getFormattedTime(new Date());
    }
}
