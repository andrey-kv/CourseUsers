package com.learnspring.courseUsers.repository;

import com.learnspring.courseUsers.model.Course;
import com.learnspring.courseUsers.model.Level;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface CourseRepositoryCustom {
    List<Course> getTopCourses(int top, Level level);
    List<Course> getSuggestedCourses(Level level);
}
