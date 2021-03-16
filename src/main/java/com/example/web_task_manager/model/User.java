package com.example.web_task_manager.model;

import com.example.web_task_manager.Properties;

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
    public static final User DEFAULT_USER = new User();
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


    @OneToMany(mappedBy = "user")
    private List<Task> tasks;

    public User() {

    }

    public User(String name, String encPassword) {
        this(name, encPassword, Properties.DEFAULT_MAIL_IN);
    }

    public User(String name, String encPassword, String mail) {
        this.name = name;
        this.encPassword = encPassword;
        this.mail = mail;
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