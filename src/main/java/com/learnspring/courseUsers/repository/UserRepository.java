package com.learnspring.courseUsers.repository;

import com.learnspring.courseUsers.model.JobPosition;
import com.learnspring.courseUsers.model.Level;
import com.learnspring.courseUsers.model.Status;
import com.learnspring.courseUsers.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String>, UserRepositoryCustom {

    String ADDRESS_CITY_LVIV = "Lviv";
    String ADDRESS_COUNTRY_UKRAINE = "Ukraine";

    User findBy_id(ObjectId id);
    // List<User> findAll();

    @Query("{ 'login' : {$regex: ?0, $options: 'i' }}")
    User findByLogin(String login);

    // @Query("{ 'address.zipCode' : ?0}")
    List<User> findByAddressZipCode(String zipCode);
    List<User> findByAddress_ZipCode(String zipCode);

    List<User> getUsersByJobPosition(JobPosition jobPosition);
    List<User> getUsersByLevel(Level level);
    List<User> getUsersByStatus(Status status);

    void deleteBy_id(ObjectId id);
    void deleteByLogin(String login);



}

