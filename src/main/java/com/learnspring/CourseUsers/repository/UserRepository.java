package com.learnspring.CourseUsers.repository;

import com.learnspring.CourseUsers.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String>, UserRepositoryCustom {

    User findBy_id(ObjectId id);

    @Query("{ 'login' : {$regex: ?0, $options: 'i' }}")
    User findByLogin(String login);

    @Query("{ 'address.zipCode' : ?0}")
    List<User> findByAddressZipCode(String zipCode);

    void deleteBy_id(ObjectId id);
    void deleteByLogin(String login);
}

