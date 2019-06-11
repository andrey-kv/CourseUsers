package com.learnspring.CourseUsers.repository;

import com.learnspring.CourseUsers.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class UserRepositoryTest {

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    @Test
    public void findByLogin() {
        User actual = userRepository.findByLogin("maksym");
        Assert.assertEquals("Maksym", actual.getFirstName());
        Assert.assertEquals("Vilkin", actual.getLastName());
    }
}