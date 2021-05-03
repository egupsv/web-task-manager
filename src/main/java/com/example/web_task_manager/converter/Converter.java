package com.example.web_task_manager.converter;

import com.example.web_task_manager.model.Task;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Converter {
    public static void convertObjectToXml(Task task, String fileName, HttpServletResponse response) {
        try (ServletOutputStream sos = response.getOutputStream()) {
            File file = new File(fileName);
            JAXBContext context = JAXBContext.newInstance(Task.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(task, file);
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

    public static void convertObjectToXml(ArrayList<Task> tasks, String fileName, HttpServletResponse response) {
        try (ServletOutputStream sos = response.getOutputStream()) {
            File file = new File(fileName);
            TasksForXml tasksForXml = new TasksForXml(tasks);
            JAXBContext context = JAXBContext.newInstance(Task.class, TasksForXml.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(tasksForXml, file);
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
}
