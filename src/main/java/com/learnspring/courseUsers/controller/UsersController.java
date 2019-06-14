package com.learnspring.courseUsers.controller;

import com.learnspring.courseUsers.exception.NoSuchUserException;
import com.learnspring.courseUsers.model.User;
import com.learnspring.courseUsers.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping(value = "/{id}")
    public User findUser(@PathVariable("id") String id) {
        log.info("Find by id: " + id);
        User result = userRepository.findBy_id(new ObjectId(id));
        if (result == null) {
            throw new NoSuchUserException();
        }
        return result;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        log.info("Posting user: " + user.toString());
        return userRepository.save(user);
    }

    @DeleteMapping(value = "/{id}")
    public User deleteUser(@PathVariable("id") String id) {
        log.info("Deleting user with id: " + id);
        User user = userRepository.findBy_id(new ObjectId(id));
        if (user != null) {
            userRepository.deleteBy_id(new ObjectId(id));
            return user;
        } else {
            throw new NoSuchUserException();
        }
    }

}
