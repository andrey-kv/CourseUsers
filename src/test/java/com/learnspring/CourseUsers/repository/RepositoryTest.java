package com.learnspring.CourseUsers.repository;

import com.learnspring.CourseUsers.model.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Slf4j
public class RepositoryTest {

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CourseRepository courseRepository;

    @Before
    public void prepareDatabase() {
        createUsers();
        createCourses();
    }

    @Test
    public void getTopCourses() {
        List<Course> top2 = courseRepository.getTopCourses(2, Level.MIDDLE);
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
        List<User> lvivs = userRepository.findUsersByCity("Lviv");
        Assert.assertEquals(4, lvivs.size());
        for (User user : lvivs) {
            Assert.assertEquals("Lviv", user.getAddress().getCity());
        }
    }

    @Test
    public void getUsersByLevel() {
        testByLevel(Level.MIDDLE, 3);
        testByLevel(Level.SENIOR, 2);
    }

    private void testByLevel(Level level, int count) {
        List<User> users = userRepository.getUsersByLevel(level);
        Assert.assertEquals(count, users.size());
        for (User user : users) {
            Assert.assertEquals(level, user.getLevel());
        }
    }

    @After
    public void dropDatabase() {
        // mongoTemplate.getDb().drop();
    }

    private void createUsers() {
        log.info("Creating Users.");
        List<User> users = new ArrayList<>();
        users.add(new User("Andrii", "andrii@gmail.com", "Andrii", "Lozhkin",
                Status.ACTIVE, JobPosition.QA_ENGINEER, Level.MIDDLE));
        users.add(new User("maksym", "maksim@gmail.com", "Maksym", "Vilkin",
                Status.CREATED, JobPosition.SOFTWARE_ENGINEER, Level.MIDDLE));
        users.add(new User("ural", "afterpaty@gmail.com", "Sergiy", "Uralov",
                Status.CREATED, JobPosition.NETWORK_ENGINEER, Level.SENIOR));
        users.add(new User("alexpar", "alexpar@gmail.com", "Oleksiy", "Parmezanenko",
                Status.APPROVED, JobPosition.SOFTWARE_ENGINEER, Level.SENIOR));
        users.add(new User("tormoz", "taranenko@gmail.com", "Vitalii", "Manko",
                Status.APPROVED, JobPosition.SYSTEMS_ANALYST, Level.MIDDLE));
        users.add(new User("usr01", "a.usr01@gmail.com", "Taras", "Andrienko",
                Status.ACTIVE, JobPosition.QA_ENGINEER, Level.TRAINEE));

        User usr = new User("addr", "addr_has@gmail.com",
                "Mykola", "Lopar", Status.ACTIVE, JobPosition.SOFTWARE_ENGINEER, Level.JUNIOR);
        Address adr = new Address("Ukraine", "Lviv", "79019");
        usr.setAddress(adr);
        users.add(usr);

        for (User user : users) {
            if (userRepository.findByLogin(user.getLogin()) == null) {
                userRepository.save(user);
                log.info("Save User = " + user.toString());
            }
        }

        userRepository.updateAddressByLogin("tormoz", new Address("Ukraine", "Lviv", "79019"));
        userRepository.updateAddressByLogin("ural", new Address("Ukraine", "Lviv", "79037"));
        userRepository.updateAddressByLogin("Andrii", new Address("Ukraine", "Kyiv", "02028"));
        userRepository.updateAddressByLogin("maksym", new Address("Ukraine", "Lviv", "79019"));
    }

    private void createCourses() {
        log.info("Creating Courses.");
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("Angular 6.0", Level.MIDDLE, 22, CourseType.PERSON, 4.7));
        courses.add(new Course("Spring Cloud", Level.SENIOR, 14, CourseType.PERSON, 4.6));
        courses.add(new Course("What's new in Java 12", Level.JUNIOR, 8, CourseType.ONLINE, 4.2));
        courses.add(new Course("Streams in Java 8", Level.MIDDLE, 8, CourseType.ONLINE, 4.4));
        courses.add(new Course("Docker configuration", Level.MIDDLE, 6, CourseType.OTHER, 4.6));
        courses.add(new Course("Deploing applications to AWS", Level.MIDDLE, 16, CourseType.ONLINE, 4.9));

        for (Course course : courses) {
            if (courseRepository.findByName(course.getName()) == null) {
                courseRepository.save(course);
                log.info("Save Course = " + course.toString());
            }
        }
    }
}