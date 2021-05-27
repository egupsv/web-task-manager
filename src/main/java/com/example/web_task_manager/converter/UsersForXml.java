package com.example.web_task_manager.converter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

@XmlType
@XmlRootElement(name = "users")
public class UsersForXml implements Serializable {

    @XmlElement(name="user")
    private List<UserForXml> users;

    public UsersForXml() {}
    public UsersForXml(List<UserForXml> users) {
        this.users = users;
    }

    public List<UserForXml> getUsers() {
        return users;
    }
}