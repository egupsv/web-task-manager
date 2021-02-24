package com.example.web_task_manager.users;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The {@code User} class represents user.
 */
@Entity
@Table(name = "USERS")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * user id
     */
    private int id;
    /**
     * user's name (login)
     */

    private String name;
    /**
     * encrypted password which is used for sending to the server
     */
    private String encPassword;
    public User(){

    }
    public User(String name, String encPassword) {
        this.name = name;
        this.encPassword = encPassword;
    }

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_id_generator")
    @SequenceGenerator(name = "user_id_generator", sequenceName = "USER_ID_SEQUENCE",allocationSize = 1)
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "ENC_PASSWORD")
    public String getEncPassword() {
        return encPassword;
    }

    public void setEncPassword(String encPassword){
        this.encPassword = encPassword;
    }

}