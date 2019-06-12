package com.learnspring.CourseUsers.repository;

import com.learnspring.CourseUsers.model.Course;
import com.learnspring.CourseUsers.model.Level;
import com.learnspring.CourseUsers.model.Status;
import com.learnspring.CourseUsers.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CourseRepository courseRepository;

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

    @Test
    public void findByAddressZipCode() {
        List<User> code19 = userRepository.findByAddressZipCode("79019");
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
        List<User> users = userRepository.findSpecialQuery(32, "Lviv");
        Assert.assertEquals(1, users.size());
        for (User user : users) {
            Assert.assertTrue(user.getDateOfBirth().isBefore(LocalDate.now().minusYears(32)));
            Assert.assertEquals("Lviv", user.getAddress().getCity());
        }
    }

    @Test
    public void findSpecialBasicQuery() {
        List<User> users = userRepository.findSpecialBasicQuery(32, "Lviv");
        Assert.assertEquals(1, users.size());
        for (User user : users) {
            Assert.assertTrue(user.getDateOfBirth().isBefore(LocalDate.now().minusYears(32)));
            Assert.assertEquals("Lviv", user.getAddress().getCity());
        }
    }

}