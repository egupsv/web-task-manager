package com.example.web_task_manager.tasks;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The {@code User} class represents task.
 */

@Entity
@Table(name = "TASKS")
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
    private int id;
    /**
     * unique ID of task owner
     */


    private int userId;

    /**
     * date and time of task
     */
    private Date time;

    public Task() {

    }

    public Task(String name, String description, Date time, int userId) {
        this.name = name;
        this.time = time;
        this.description = description;
        this.userId = userId;
    }
    public Task(int id,String name, String description, Date time) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.description = description;

    }

    @Column(name = "task_name")
    public String getName() {
        return name;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    @Column(name = "completed")
    public boolean isCompleted() {
        return completed;
    }

    @Column(name = "datetime")
    public Date getTime() {
        return time;
    }

    @Id
    @Column(name = "TASK_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_id_generator")
    @SequenceGenerator(name = "task_id_generator", sequenceName = "task_ID_SEQUENCE", allocationSize = 1)
    public int getId() {
        return id;
    }

    @Column(name = "user_id",nullable = false)
    public int getUserId() {
        return userId;
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

    public void setUserId(int user_id) {
        this.userId = user_id;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                ", id=" + id +
                ", userId=" + userId +
                ", time=" + time +
                '}';
    }
}
