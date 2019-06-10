package com.learnspring.CourseUsers.service;

import com.learnspring.CourseUsers.model.Address;
import com.learnspring.CourseUsers.model.User;
import com.learnspring.CourseUsers.repository.CourseRepository;
import com.learnspring.CourseUsers.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        List<User> users = new ArrayList<User>();
        users.add(new User("Andrii", "andrii@gmail.com", "Andrii", "Lozhkin"));
        users.add(new User("maksym", "maksim@gmail.com", "Maksym", "Vilkin"));
        users.add(new User("ural", "afterpaty@gmail.com", "Sergiy", "Uralov"));
        users.add(new User("alexpar", "alexpar@gmail.com", "Oleksiy", "Parmezanenko"));

        User usr = new User("addr", "addr_has@gmail.com", "Mykola", "Lopar");
        Address adr = new Address("Ukraine", "Lviv", "79019");
        usr.setAddress(adr);
        users.add(usr);

        for (User user : users) {
            if (userRepository.findByLogin(user.getLogin()) == null) {
                userRepository.save(user);
                log.info("Save User = " + user.toString());
            }
        }

        if (userRepository.findByAddressZipCode("79019").size() > 0) {
            log.info("Found by zipCode");
        }

        userRepository.updateNameByLogin("ural", "Oleh", "Bondar");
        User oleh = userRepository.findByLogin("ural");
        log.info(oleh.getFirstName());

        log.info("=====================================================");
    }
}
