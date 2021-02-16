package com.example.web_task_manager.users;

import java.io.Serializable;

/**
 * The {@code User} class represents user.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * user's name (login)
     */
    private String name;
    /**
     * encrypted password which is used for sending to the server
     */
    private final String encPassword;

    public User(String name, String encPassword) {
        this.name = name;
        this.encPassword = encPassword;
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

}