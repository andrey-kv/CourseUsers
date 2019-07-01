package com.learnspring.courseUsers.service;

import com.learnspring.courseUsers.exception.NoSuchUserException;
import com.learnspring.courseUsers.model.LoginUserRequest;
import com.learnspring.courseUsers.model.Role;
import com.learnspring.courseUsers.model.User;
import com.learnspring.courseUsers.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String login) throws NoSuchUserException {

        log.info("loadUserByUsername, login = " + login);

        User user = repository.findByLogin(login);

        if(user == null) {
            throw new NoSuchUserException();
        }

        LoginUserRequest userRequest = LoginUserRequest.builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .authorities(Arrays.asList(Role.USER))
                .build();

        log.info("logged, details = " + userRequest.toString());

        return userRequest;
    }
}