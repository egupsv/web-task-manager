package com.example.web_task_manager.converter;

import com.example.web_task_manager.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;


@XmlRootElement(name = "user")
@XmlType(propOrder = {"name", "encPassword", "mail", "tasks"})
public class UserForXml implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(UserForXml.class);

    @XmlElement
    private String name;
    @XmlElement
    private TasksForXml tasks;
    @XmlElement
    private String encPassword;
    @XmlElement
    private String mail;



    public UserForXml() {}
    public UserForXml(String name, TasksForXml tasks) {
        this.tasks = tasks;
        this.name = name;
    }
    public UserForXml(String name, TasksForXml tasks, String encPassword, String mail) {
        this.tasks = tasks;
        this.name = name;
        this.encPassword = encPassword;
        this.mail = mail;
    }

    public TasksForXml getTasks() {
        return tasks;
    }
    public String getName() {
        return name;
    }
    public String getEncPassword() {
        return encPassword;
    }
    public String getMail() {
        return mail;
    }

    private void beforeMarshal(Marshaller marshaller) {
        if(name != null && mail == null) {
            log.info("import without adminInfo");
        }
    }
}