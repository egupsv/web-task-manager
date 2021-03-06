package com.example.web_task_manager.tasks;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String getFormattedTime(Task task) {
        Date time = task.getTime();
        return new SimpleDateFormat("dd.MM.yyyy HH:mm").format(time);
    }

    public static String getState(Task task) {
        return task.isCompleted() ? "complete" : "incomplete";
    }
}
