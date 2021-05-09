package com.example.web_task_manager.converter;

import com.example.web_task_manager.model.Task;
import com.example.web_task_manager.model.User;

import javax.ejb.Stateless;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class Converter {
    public void convertObjectToXml(List<User> exportedUsers, Task exportedTask, String fileName, HttpServletResponse response) {
        try (ServletOutputStream sos = response.getOutputStream()) {
            File file = new File(fileName);
            List<UserForXml> users = new ArrayList<UserForXml>();
            for (User user : exportedUsers) {
                List<Task> tasks = new ArrayList<>();
                if(exportedTask == null) {
                    tasks = user.getTasks();
                } else {
                    tasks.add(exportedTask);
                }
                TasksForXml tasksForXml = new TasksForXml(tasks);
                UserForXml userForXml = new UserForXml(user.getName(), tasksForXml);
                users.add(userForXml);
            }
            UsersForXml usersForXml = new UsersForXml(users);
            JAXBContext context = JAXBContext.newInstance(Task.class, TasksForXml.class, UserForXml.class, UsersForXml.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            //marshaller.setProperty("com.sun.xml.bind.xmlHeaders", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            marshaller.marshal(usersForXml, file);
            response.setContentLength((int) file.length());
            FileInputStream fileInputStream = new FileInputStream(file);
            try(BufferedInputStream bis = new BufferedInputStream(fileInputStream)) {
                int readBytes = 0;
                while ((readBytes = bis.read()) != -1)
                    sos.write(readBytes);
                sos.flush();
            }
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
    }

    public UsersForXml convertXmlToObject(InputStream fileContent) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Task.class, TasksForXml.class, UserForXml.class, UsersForXml.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (UsersForXml) unmarshaller.unmarshal(fileContent);
    }
}
