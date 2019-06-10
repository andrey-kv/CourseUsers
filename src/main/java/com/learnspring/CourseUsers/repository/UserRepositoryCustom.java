package com.learnspring.CourseUsers.repository;

import org.bson.types.ObjectId;

public interface UserRepositoryCustom {
    void updateNameByLogin(String login, String newFirstName);
    void updateNameByLogin(String login, String newFirstName, String newLastName);
    // void deleteBy_id(ObjectId id);
}
