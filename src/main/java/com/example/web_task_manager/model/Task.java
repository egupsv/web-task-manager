package com.example.web_task_manager.model;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The {@code User} class represents task.
 */

@Entity
@Table(name = "TASKS")
public class Task implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String TASK_NAME_COLUMN = "task_name";
    private static final String DESCRIPTION_COLUMN = "description";
    private static final String COMPLETED_COLUMN = "completed";
    private static final String DATE_COLUMN = "datetime";
    private static final String TASK_ID_COLUMN = "TASK_ID";
    private static final String USER_ID_COLUMN = "user_id";

    /**
     * unique ID of task
     */
    @Id
    @Column(name = TASK_ID_COLUMN)
    private int id;

    /**
     * the name of task
     */
    @Column(name = TASK_NAME_COLUMN)
    private String name;

    /**
     * short description of task (any important information)
     */
    @Column(name = DESCRIPTION_COLUMN)
    private String description;

    /**
     * status of task
     */
    @Column(name = COMPLETED_COLUMN)
    private boolean completed = false;


    /**
     * unique ID of task owner
     */

    @ManyToOne
    @JoinColumn(name = USER_ID_COLUMN, nullable = false)
    private User user;

    /**
     * date and time of task
     */
    @Column(name = DATE_COLUMN, nullable = false)
    private Date time;

    public Task() {

    }

    public Task(String name, String description, Date time, User user) {
        this.name = name;
        this.time = time;
        this.description = description;
        this.user = user;
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


    public int getId() {
        return id;
    }


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


    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                ", id=" + id +
                ", userId=" + user.getId() +
                ", time=" + time +
                '}';
    }
}
