package com.learnspring.CourseUsers.repository;

import com.learnspring.CourseUsers.model.Course;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {
    Course findBy_id(ObjectId id);
}