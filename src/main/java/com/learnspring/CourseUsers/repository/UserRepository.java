package com.learnspring.CourseUsers.repository;

import com.learnspring.CourseUsers.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findBy_id(ObjectId id);
}

