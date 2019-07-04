package com.learnspring.courseUsers.repository;

import com.learnspring.courseUsers.model.*;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners(value = {DatabasePreparer.class, DependencyInjectionTestExecutionListener.class})
@ContextConfiguration
@Slf4j
public class RepositoryTest {

    public static final String TEMPORARY_LOGIN = "deluser";

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    public void getTopCourses() {
        List<Course> top2 = courseRepository.getTopCourses(2, Level.L2_MIDDLE);
        Assert.assertEquals(2, top2.size());
        Assert.assertEquals(4.9, top2.get(0).getRating(), 0);
        Assert.assertEquals(4.7, top2.get(1).getRating(), 0);
    }

    @Test
    public void usersFindByLogin() {
        User user1 = userRepository.findByLogin("maksym");
        Assert.assertEquals("Maksym", user1.getFirstName());
        Assert.assertEquals("Vilkin", user1.getLastName());

        User user2 = userRepository.findByLogin("ALEXPAR");
        Assert.assertEquals("Oleksiy", user2.getFirstName());

        User user3 = userRepository.findByLogin("not_exist");
        Assert.assertNull(user3);
    }

    @Test
    public void findUsersByCity() {
        List<User> lvivs = userRepository.findUsersByCity(UserRepository.ADDRESS_CITY_LVIV);
        Assert.assertEquals(4, lvivs.size());
        for (User user : lvivs) {
            Assert.assertEquals(UserRepository.ADDRESS_CITY_LVIV, user.getAddress().getCity());
        }
    }

    @Test
    public void getUsersByLevel() {
        testByLevel(Level.L2_MIDDLE, 3);
        testByLevel(Level.L3_SENIOR, 2);
    }

    private void testByLevel(Level level, int count) {
        List<User> users = userRepository.getUsersByLevel(level);
        Assert.assertEquals(count, users.size());
        for (User user : users) {
            Assert.assertEquals(level, user.getLevel());
        }
    }

    @Test
    public void findByAddressZipCode() {
        List<User> code19 = userRepository.findByAddressZipCode("79019");
        Assert.assertEquals(3, code19.size());
        Assert.assertTrue(code19.stream().filter(u -> u.getLogin().equals("tormoz")).findAny().isPresent());
    }

    @Test
    public void findByAddress_ZipCode() {
        List<User> code19 = userRepository.findByAddress_ZipCode("79019");
        // TODO: Looks like the same with findByAddressZipCode()
        Assert.assertEquals(3, code19.size());
        Assert.assertTrue(code19.stream().filter(u -> u.getLogin().equals("tormoz")).findAny().isPresent());
    }

    @Test
    public void updateNameByLogin() {
        User user0 = userRepository.findByLogin("usr01");
        userRepository.updateNameByLogin("usr01", "Oleg", "Bondar");
        User user1 = userRepository.findByLogin("usr01");
        Assert.assertEquals(user0.get_id(), user1.get_id());
        Assert.assertNotEquals(user0.getFirstName(), user1.getFirstName());
        Assert.assertNotEquals(user0.getLastName(), user1.getLastName());

        userRepository.updateNameByLogin("usr01", "Oleh");
        User user2 = userRepository.findBy_id(user0.get_id());
        Assert.assertEquals("Oleh", user2.getFirstName());
        Assert.assertEquals("Bondar", user2.getLastName());
    }

    @Test
    public void approveActiveUsers() {
        List<User> active = userRepository.getUsersByStatus(Status.ACTIVE);
        userRepository.approveActiveUsers();
        for (User user : active) {
            User modified = userRepository.findBy_id(user.get_id());
            Assert.assertEquals(Status.APPROVED, modified.getStatus());
        }
        active = userRepository.getUsersByStatus(Status.ACTIVE);
        Assert.assertEquals(0, active.size());
    }

    @Test
    public void findSpecialQuery() {

        List<User> users = userRepository.findSpecialQuery(32, UserRepository.ADDRESS_CITY_LVIV);
        Assert.assertEquals(1, users.size());
        for (User user : users) {
            Assert.assertTrue(user.getDateOfBirth().isBefore(LocalDate.now().minusYears(32)));
            Assert.assertEquals(UserRepository.ADDRESS_CITY_LVIV, user.getAddress().getCity());
        }
    }

    @Test
    public void findSpecialBasicQuery() {
        List<User> users = userRepository.findSpecialBasicQuery(32, UserRepository.ADDRESS_CITY_LVIV);
        Assert.assertEquals(1, users.size());
        for (User user : users) {
            Assert.assertTrue(user.getDateOfBirth().isBefore(LocalDate.now().minusYears(32)));
            Assert.assertEquals(UserRepository.ADDRESS_CITY_LVIV, user.getAddress().getCity());
        }
    }

    @Test
    public void findUsersWhenLevelGreaterThen() {
        List<User> users = userRepository.findUsersWhenLevelGreaterThen(Level.L2_MIDDLE);
        for (User user : users) {
            Assert.assertEquals(Level.L3_SENIOR, user.getLevel());
        }
    }

    @Test
    public void getUsersByJobPosition() {
        List<User> users = userRepository.getUsersByJobPosition(JobPosition.QA_ENGINEER);
        Assert.assertEquals(2, users.size());
        for (User user : users) {
            Assert.assertEquals(JobPosition.QA_ENGINEER, user.getJobPosition());
        }
        users = userRepository.getUsersByJobPosition(JobPosition.SOFTWARE_ENGINEER);
        Assert.assertEquals(3, users.size());
        for (User user : users) {
            Assert.assertEquals(JobPosition.SOFTWARE_ENGINEER, user.getJobPosition());
        }
    }

    @Test
    public void showPagination() {

        Pageable pageable = PageRequest.of(0, 2, Sort.by("login").descending());

        int counter = 0;

        while (true) {
            Page<User> page = userRepository.getUsersByCity(UserRepository.ADDRESS_CITY_LVIV, pageable);
            List<User> pageContent = page.getContent();
            int pageNumber = page.getNumber();
            Assert.assertEquals(counter, pageNumber);
            Assert.assertEquals(2, pageContent.size());
            if (!page.hasNext()) {
                break;
            }
            pageable = page.nextPageable();
            counter++;
        }
    }

    @Test
    public void deleteById() {

        User user = new User(TEMPORARY_LOGIN, "deluser@gmail.com", "Anton", "Khodz",
                Status.ACTIVE, JobPosition.QA_ENGINEER, Level.L0_TRAINEE, LocalDate.parse("1990-03-03"), "password");

        userRepository.save(user);
        ObjectId id =  userRepository.findByLogin(TEMPORARY_LOGIN).get_id();
        Assert.assertNotNull(id);
        userRepository.deleteBy_id(id);
        Assert.assertNull(userRepository.findByLogin(TEMPORARY_LOGIN));

    }

    @Test
    public void deleteByLogin() {

        User user = new User(TEMPORARY_LOGIN, "deluser@gmail.com", "Anton", "Khodz",
                Status.ACTIVE, JobPosition.QA_ENGINEER, Level.L0_TRAINEE, LocalDate.parse("1990-03-03"), "password");

        userRepository.save(user);
        Assert.assertNotNull(userRepository.findByLogin(TEMPORARY_LOGIN));
        userRepository.deleteByLogin(TEMPORARY_LOGIN);
        Assert.assertNull(userRepository.findByLogin(TEMPORARY_LOGIN));
    }

}