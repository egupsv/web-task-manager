package com.example.web_task_manager.converter;

import com.example.web_task_manager.model.Task;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

@XmlType
@XmlRootElement(name = "tasks")
public class TasksForXml implements Serializable {

    @XmlElement(name="task")
    private List<Task> tasks;

    public TasksForXml() {}
    public TasksForXml(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
