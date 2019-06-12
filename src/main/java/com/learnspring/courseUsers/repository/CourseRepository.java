package com.learnspring.courseUsers.repository;

import com.learnspring.courseUsers.model.Course;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends MongoRepository<Course, String>, CourseRepositoryCustom {
    Course findBy_id(ObjectId id);
    Course findByName(String name);
}