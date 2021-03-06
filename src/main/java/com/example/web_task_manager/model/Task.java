package com.example.web_task_manager.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;

/**
 * The {@code User} class represents task.
 */

@Entity
@Table(name = "TASKS")
@XmlRootElement(name = "Task")
@XmlType(propOrder = {"name", "description", "completed", "time"})
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

    public Task(String name, String description, Date time, boolean completed, User user) {
        this.name = name;
        this.time = time;
        this.description = description;
        this.completed = completed;
        this.user = user;
    }


    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }


    public boolean getCompleted() {
        return completed;
    }


    public Date getTime() {
        return time;
    }

    @XmlTransient
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

    @XmlTransient
    public int getUserId() {
        return user.getId();
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean equals(Task task) {
        return (getName().equals(task.getName()) &&
                getDescription().equals(task.getDescription()) &&
                getTime().equals(task.getTime()));
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
