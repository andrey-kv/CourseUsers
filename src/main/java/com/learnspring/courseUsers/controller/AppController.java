package com.learnspring.courseUsers.controller;

import com.learnspring.courseUsers.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@Slf4j
public class AppController {

    @PostMapping(value = "/login")
    public String login(@RequestBody User user) {
        log.info("Posting user: " + user.toString());
        return "Login";
    }

}
