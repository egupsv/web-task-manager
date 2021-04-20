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

public class Converter {
    public static void convertObjectToXml(Task task, String fileName, HttpServletResponse response) {
        try {
            File file = new File(fileName);
            JAXBContext context = JAXBContext.newInstance(Task.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(task, file);
            ServletOutputStream sos = response.getOutputStream();
            response.setContentLength((int) file.length());
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fileInputStream);
            int readBytes = 0;
            while ((readBytes = bis.read()) != -1)
                sos.write(readBytes);
            sos.flush();
            sos.close();
            bis.close();
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
    }
}
