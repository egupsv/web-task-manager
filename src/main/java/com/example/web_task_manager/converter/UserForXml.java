package com.example.web_task_manager.converter;

import com.example.web_task_manager.model.Task;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "user")
@XmlType (propOrder = {"name", "tasks"})
public class UserForXml implements Serializable {
    @XmlElement
    private String name;
    @XmlElement
    private TasksForXml tasks;

    public UserForXml() {}
    public UserForXml(String name, TasksForXml tasks) {
        this.tasks = tasks;
        this.name = name;
    }

    public TasksForXml getTasks() {
        return tasks;
    }
    public String getName() { return name; }
}
