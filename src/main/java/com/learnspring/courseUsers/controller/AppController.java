package com.learnspring.courseUsers.controller;

import com.learnspring.courseUsers.model.User;
import com.learnspring.courseUsers.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/")
@Slf4j
public class AppController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/login")
    public ResponseEntity login(HttpServletRequest request, HttpServletResponse response) {

        log.info("Login attempt " + request.toString());
        Authentication auth = new UsernamePasswordAuthenticationFilter().attemptAuthentication(request, response);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", sc);
        return ResponseEntity.ok("OK");
    }

    @PostMapping(value = "/register")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

}
