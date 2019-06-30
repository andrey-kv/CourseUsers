package com.learnspring.courseUsers.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Slf4j
public class AppController {

    @PostMapping(value = "/test")
    ResponseEntity<String> test() {
        return ResponseEntity.ok("You can only see this after a successful login :)");
    }
}
