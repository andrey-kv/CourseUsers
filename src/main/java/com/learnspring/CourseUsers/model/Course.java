package com.learnspring.CourseUsers.model;

import lombok.Data;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
public class Course {

    @Id
    @Getter
    private ObjectId _id;

    private String name;
    private String level;
    private int duration;
    private CourseType courseType;
    private List<String> tagDescription;
    private double rating;

}
