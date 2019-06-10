package com.learnspring.CourseUsers.repository;

import com.learnspring.CourseUsers.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepositoryCustom {
    void updateNameByLogin(String login, String newFirstName, String nesSecondName);
}
