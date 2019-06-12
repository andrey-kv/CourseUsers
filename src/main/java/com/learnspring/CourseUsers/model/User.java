package com.learnspring.CourseUsers.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "users")
@Data
@NoArgsConstructor
public class User {

    public User(String login, String mail, String firstName, String lastName, Status status,
                JobPosition jobPosition, Level level, LocalDate dateOfBirth) {
        this.login = login;
        this.mail = mail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.jobPosition = jobPosition;
        this.level = level;
        this.dateOfBirth = dateOfBirth;
    }

    @Id
    @Getter
    private ObjectId _id;

    @Indexed
    private String login;
    private String mail;
    private String firstName;
    private String lastName;

    private Address address;
    private LocalDate dateOfBirth;

    private List<String> phones;
    private Status status;
    private JobPosition jobPosition;

    @Indexed
    private Level level;

}
