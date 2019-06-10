package com.learnspring.CourseUsers.repository;

public interface UserRepositoryCustom {
    void updateNameByLogin(String login, String newFirstName, String newSecondName);
}
