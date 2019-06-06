package com.learnspring.CourseUsers.service;

import com.learnspring.CourseUsers.model.User;
import com.learnspring.CourseUsers.repository.CourseRepository;
import com.learnspring.CourseUsers.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ExecService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    @Qualifier("middleDev")
    private User firstUser;

    @EventListener(ApplicationReadyEvent.class)
    private void exec() {
        log.info("========= Execution CourseUsers Application =========");

        List<User> users = userRepository.findAll();
        if (users.size() == 0) {
            userRepository.save(firstUser);
        } else {
            for (User user : users) {
                user.setFirstName("Andrii");
                ObjectId id = user.get_id();
            }
            userRepository.saveAll(users);
        }
        log.info("FirstUser = " + firstUser.toString());
    }
}
