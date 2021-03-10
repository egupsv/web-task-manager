package com.example.web_task_manager;

import com.example.web_task_manager.model.Task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class Utils {
    public static final Pattern REGEX_LOGIN_PATTERN = Pattern.compile("^[A-Za-z0-9]{4,16}$");

    public static String getFormattedTime(Task task) {
        return getFormattedTime(task.getTime());
    }

    public static String getFormattedTime(Date date) {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm").format(date);
    }

    public static String getState(Task task) {
        return task.isCompleted() ? "complete" : "incomplete";
    }

    public static String getCurrentDateString() {
        return getFormattedTime(new Date());
    }
}
