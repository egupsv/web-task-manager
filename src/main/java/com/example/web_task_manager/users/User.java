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

    public static final String NAME_COLUMN = "NAME";
    public static final String ID_COLUMN = "USER_ID";
    public static final String PASSWORD_COLUMN = "ENC_PASSWORD";

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
    @Column(name = ID_COLUMN)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_id_generator")
    @SequenceGenerator(name = "user_id_generator", sequenceName = "USER_ID_SEQUENCE",allocationSize = 1)
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    @Column(name = NAME_COLUMN,unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = PASSWORD_COLUMN)
    public String getEncPassword() {
        return encPassword;
    }

    public void setEncPassword(String encPassword){
        this.encPassword = encPassword;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", encPassword='" + encPassword + '\'' +
                '}';
    }
}