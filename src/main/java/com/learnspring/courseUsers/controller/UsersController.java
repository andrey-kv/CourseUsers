package com.learnspring.courseUsers.controller;

import com.learnspring.courseUsers.model.User;
import com.learnspring.courseUsers.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> findAll() {
        List<User> result = userRepository.findAll();
        log.info("Find all: Found " + result.size() + " record(s)");
        return result;
    }

    @GetMapping(value = "/{login}")
    public User findByLogin(@PathVariable("login") String login) {
        User result = userRepository.findByLogin(login);
        log.info("Find by login: " + login);
        return result;
    }

}
