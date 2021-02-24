package com.example.web_task_manager.users;
import java.io.*;
import java.util.*;

/**
 * The {@code Users} class represents list of users.
 * this list is stored in file which is available for server
 */
public class Users implements Serializable {
    /**
     * list of users itself
     */
    private List<User> users;
    private static final long serialVersionUID = 1L;
    /**
     * path to file which contains list of users
     */
    //private final String path;

    public Users() throws IOException {
        /*this.path = path;
        BufferedReader br = new BufferedReader(new FileReader(path));
        if (br.readLine() == null) {*/
            this.users = new ArrayList<>();
        /*} else {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {

                this.users = (List<User>) ois.readObject();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }*/
    }

    /**
     * adds user to current list
     * @param   user new user
     */
    public void addUser(User user) {
        this.users.add(user);
    }

    /**
     * gets user by login (name)
     * @param   login user's login
     * @return  required user
     */
    public User getUser(String login) {
        for (User user : users) {
            if (user.getName().equals(login)) {
                return user;
            }
        }
        return null;
    }

    /**
     * saves the file with the current list to disk
     */
   /* public void save() {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path)))
        {
            oos.writeObject(users);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }*/
}
