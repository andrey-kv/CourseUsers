package com.learnspring.CourseUsers.repository;

import com.learnspring.CourseUsers.model.Course;
import com.learnspring.CourseUsers.model.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class CourseRepositoryImpl implements CourseRepositoryCustom {

    private final MongoTemplate mongoTemplate;
    private final MongoOperations mongoOperations;

    public CourseRepositoryImpl(@Autowired final MongoTemplate mongoTemplate,
                              @Autowired final MongoOperations mongoOperations) {
        this.mongoTemplate = mongoTemplate;
        this.mongoOperations = mongoOperations;
    }

    @Override
    public List<Course> getTopCourses(int top, Level level) {
        Pageable pageable = PageRequest.of(0, top, Sort.by("rating").descending());
        Query query = new Query().query(Criteria.where("level").is(level)).with(pageable);
        List<Course> list = mongoOperations.find(query, Course.class);
        long count = mongoOperations.count(query, Course.class);
        Page<Course> resultPage = new PageImpl<>(list , pageable, count);
        return resultPage.getContent();
    }

    @Override
    public List<Course> getSuggestedCourses(Level level) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("rating").descending());
        Query query = new Query().query(Criteria.where("level").is(level)).with(pageable);
        List<Course> list = mongoOperations.find(query, Course.class);
        long count = mongoOperations.count(query, Course.class);
        Page<Course> resultPage = new PageImpl<>(list , pageable, count);
        return resultPage.getContent();
    }

}
