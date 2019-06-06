package com.learnspring.CourseUsers.model;

import lombok.Data;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document
@Data
public class User {

    @Id
    @Getter
    private ObjectId _id;

    private String login;
    private String mail;
    private String firstName;
    private String lastName;

    private Address address;
    private Date dateOfBirth;

    private List<String> phones;
    private Status status;
    private JobPosition jobPosition;
    private Level level;

}
