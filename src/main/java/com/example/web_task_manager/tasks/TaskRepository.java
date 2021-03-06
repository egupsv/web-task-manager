package com.example.web_task_manager.tasks;


import java.io.*;
import java.util.*;

/**
 * The {@code Users} class represents repository of tasks.
 * this repository is stored in separate file for every single user
 */
public class TaskRepository implements Serializable {
    /**
     * list of tasks
     */
    private final List<Task> tasks;


    private static final long serialVersionUID = 1L;

    /**
     * path to user's file which contains repository
     */
    //private final String path;

     public TaskRepository() throws IOException {
        /*this.path = path;
        BufferedReader br = new BufferedReader(new FileReader(path));
        if (br.readLine() == null) {*/
            this.tasks = new ArrayList<>();
        /*} else {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
                this.tasks = (List<Task>) ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
    }

    /**
     * calculate value of last ID
     * @return value of last ID
     */
    public int calculateMaxId() {
        int maxId = 0;
        for (Task task : tasks) {
            int id = task.getId();
            if (id > maxId) {
                maxId = id;
            }
        }
        return maxId;
    }

    /**
     * @return list of tasks
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * adds new task to repository
     * @param task new task
     */
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    /**
     * @param name name of task
     * @return list of tasks with specific name
     */
    public List<Task> getTasksByName(String name) {
        List<Task> result = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getName().equals(name)) result.add(task);
        }
        return result;
    }

    /**
     * @param id ID of task
     * @return list of tasks with specific ID
     */
    public Task getTaskByID(int id) {
        Task result = null;
        for (Task task : tasks) {
            if (task.getId() == id) result = task;
        }
        return result;
    }

    /**
     * remove task(s) from repository
     * @param requiredTasks list of tasks which must be removed
     */
    public void removeTasks(List<Task> requiredTasks) {
        tasks.removeAll(requiredTasks);
    }

    public void removeTask(int id) {
        Task task= getTaskByID(id);
        tasks.remove(task);
    }

    /**
     * makes any task completed
     * @param task task which must be completed
     */
    public void changeTaskState(int id) {
        Task task= getTaskByID(id);
        task.setCompleted(!task.isCompleted());
    }

    /**
     * clear task repository
     */
    public void clear() {
        tasks.clear();
    }

    /**
     * saves current repository to disk
     */
    /*public void save() {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path)))
        {
            oos.writeObject(tasks);
            System.out.println("Data has been successfully saved to disk");
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }*/
}
