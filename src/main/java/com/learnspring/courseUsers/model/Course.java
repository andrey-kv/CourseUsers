package com.learnspring.courseUsers.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "courses")
@Data
@NoArgsConstructor
public class Course {

    public Course(String name, Level level, int duration, CourseType courseType, double rating) {
        this.name = name;
        this.level = level;
        this.duration = duration;
        this.courseType = courseType;
        this.rating = rating;
    }

    @Id
    @Getter
    private ObjectId _id;

    private String name;
    private Level level;
    private int duration;
    private CourseType courseType;
    private List<String> tagDescription;
    private double rating;

}
