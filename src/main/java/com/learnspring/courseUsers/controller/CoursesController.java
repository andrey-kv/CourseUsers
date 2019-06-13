package com.learnspring.courseUsers.controller;

import com.learnspring.courseUsers.model.Course;
import com.learnspring.courseUsers.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/courses")
@Slf4j
public class CoursesController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public List<Course> findAll() {
        List<Course> result = courseRepository.findAll();
        log.info("Find all: Found " + result.size() + " record(s)");
        return result;
    }
}
