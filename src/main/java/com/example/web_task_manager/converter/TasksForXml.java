package com.example.web_task_manager.converter;

import com.example.web_task_manager.model.Task;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlType(name = "tasks")
@XmlRootElement
public class TasksForXml implements Serializable {

    @XmlElement(name="task")
    private final ArrayList<Task> tasks;

    public TasksForXml(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
