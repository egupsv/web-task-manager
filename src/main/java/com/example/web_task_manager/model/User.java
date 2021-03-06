package com.example.web_task_manager.model;

import com.example.web_task_manager.Properties;
import com.example.web_task_manager.users.Role;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * The {@code User} class represents user.
 */
@Entity
@Table(name = "USERS")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String NAME_COLUMN = "NAME";
    public static final String ID_COLUMN = "USER_ID";
    public static final String PASSWORD_COLUMN = "ENC_PASSWORD";
    public static final String MAIL_COLUMN = "MAIL";
    public static final String ROLE_COLUMN = "ROLE";

    public static final int MIN_USER_NAME = 4;
    /**
     * user id
     */
    @Id
    @Column(name = ID_COLUMN, unique = true)
    private int id;
    /**
     * user's name (login)
     */
    @Column(name = NAME_COLUMN, unique = true)
    private String name;
    /**
     * encrypted password which is used for sending to the server
     */
    @Column(name = PASSWORD_COLUMN)
    private String encPassword;

    /**
     * Mail of user
     */
    @Column(name = MAIL_COLUMN, unique = true)
    private String mail;

    /**
     * Role of user ("admin" or "user")
     */
    @Column(name = ROLE_COLUMN)
    private String role;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Task> tasks;

    public User() {

    }

    public User(String name, String encPassword) {
        this(name, encPassword, Properties.DEFAULT_MAIL_IN);
    }

    public User(String name, String encPassword, String mail) {
        this(name, encPassword, mail, Role.DEFAULT_ROLE.toString());
    }

    public User(String name, String encPassword, String mail, String role) {
        this.name = name;
        this.encPassword = encPassword;
        this.mail = mail;
        this.role = role;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEncPassword() {
        return encPassword;
    }

    public void setEncPassword(String encPassword) {
        this.encPassword = encPassword;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public boolean containsTask(Task taskForCheck) {
        List<Task> tasks = getTasks();
        for (Task task : tasks) {
            if (taskForCheck.equals(task)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", encPassword='" + encPassword + '\'' +
                ", mail='" + mail + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}