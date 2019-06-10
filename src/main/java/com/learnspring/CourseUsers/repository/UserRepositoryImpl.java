package com.learnspring.CourseUsers.repository;

import com.learnspring.CourseUsers.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class UserRepositoryImpl implements UserRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public UserRepositoryImpl(@Autowired final MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void updateNameByLogin(String login, String newFirstName) {

        mongoTemplate.updateFirst(Query.query(Criteria.where("login").is(login)),
                       Update.update("firstName", newFirstName), User.class);
    }

    @Override
    public void updateNameByLogin(String login, String newFirstName, String newLastName) {
        Update update = new Update();
        update.set("firstName",newFirstName);
        update.set("lastName", newLastName);

        mongoTemplate.updateFirst(Query.query(Criteria.where("login").is(login)), update, User.class);
     }
}
