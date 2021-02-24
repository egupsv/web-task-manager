package com.gmail.egupovsv89.task_manager.tasks;

import java.io.Serializable;
import java.util.Date;

/**
 * The {@code User} class represents task.
 */
public class Task implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * the name of task
     */
    private String name;

    /**
     * short description of task (any important information)
     */
    private String description;

    /**
     * status of task
     */
    private boolean completed = false;

    /**
     * unique ID
     */
    private final int id;

    /**
     * date and time of task
     */
    private Date time;

    public Task(String name, String description, Date time, int id) {
        this.name = name;
        this.time = time;
        this.description = description;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public Date getTime() {
        return time;
    }

    public int getId() { return id; }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                ", time=" + time +
                '}';
    }
}
