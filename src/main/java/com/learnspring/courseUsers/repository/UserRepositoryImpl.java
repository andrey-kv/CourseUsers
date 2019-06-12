package com.learnspring.courseUsers.repository;

import com.learnspring.courseUsers.model.Address;
import com.learnspring.courseUsers.model.Level;
import com.learnspring.courseUsers.model.Status;
import com.learnspring.courseUsers.model.User;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final MongoTemplate mongoTemplate;
    private final MongoOperations mongoOperations;

    public UserRepositoryImpl(@Autowired final MongoTemplate mongoTemplate,
                              @Autowired final MongoOperations mongoOperations) {
        this.mongoTemplate = mongoTemplate;
        this.mongoOperations = mongoOperations;
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

    @Override
    public List<User> findUsersWhenLevelGreaterThen(Level level) {
        return mongoTemplate.find(Query.query(Criteria.where("level").gt(level)), User.class);
    }

    @Override
    public void updateAddressByLogin(String login, Address address) {
        mongoTemplate.updateFirst(Query.query(Criteria.where("login").is(login)),
                Update.update("address", address), User.class);
    }

    @Override
    public List<User> findUsersByCity(String city) {
        return mongoTemplate.find(Query.query(Criteria.where("address.city").is(city)), User.class);
    }

    @Override
    public Page<User> getUsersByCity(String city, Pageable pageable) {
        // Pageable pageable = PageRequest.of(pageNo, itemsOnPage);
        Query query = new Query().query(Criteria.where("address.city").is(city)).with(pageable);

        List<User> list = mongoOperations.find(query, User.class);
        long count = mongoOperations.count(query, User.class);
        Page<User> resultPage = new PageImpl<User>(list , pageable, count);
        return resultPage;
    }

    @Override
    public void approveActiveUsers() {
        Query query = new Query().query(Criteria.where("status").is(Status.ACTIVE));
        Update update = new Update();
        update.set("status", Status.APPROVED);
        UpdateResult result = mongoTemplate.updateMulti(query, update, User.class);
    }

    @Override
    public List<User> findSpecialQuery(int age, String city) {
        LocalDate ageDate = LocalDate.now().minusYears(age);
        Query query = new Query().query(Criteria.where("dateOfBirth").lt(ageDate).and("address.city").is(city));
        return mongoOperations.find(query, User.class);
    }

    @Override
    public List<User> findSpecialBasicQuery(int age, String city) {
        LocalDate ageDate = LocalDate.now().minusYears(age);
        String basicQuery = "{ 'dateOfBirth' : { '$lt' : ISODate('" +
                ageDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
                + "') }, 'address.city' : '" + city + "' }" ;
        // basicQuery = "{ 'dateOfBirth' : { '$lt' : { '$java' : '2009-06-12' } }, 'address.city' : 'Lviv' }";
        // basicQuery = "{ 'dateOfBirth' : { '$lt' : ISODate('2009-06-12') }, 'address.city' : 'Lviv' }";
        BasicQuery query = new BasicQuery(basicQuery);
        return mongoOperations.find(query, User.class);
    }
}
