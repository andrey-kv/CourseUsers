package com.learnspring.CourseUsers.repository;

import com.learnspring.CourseUsers.model.Course;
import com.learnspring.CourseUsers.model.Level;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface CourseRepositoryCustom {
    List<Course> getTopCourses(int top, Level level);
    List<Course> getSuggestedCourses(Level level);
}
