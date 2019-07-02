package com.learnspring.courseUsers.configuration;

import com.learnspring.courseUsers.model.User;
import com.learnspring.courseUsers.repository.CourseRepository;
import com.learnspring.courseUsers.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("dev")
@Slf4j
public class StrartupConfigDev extends StartupConfig {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    @Qualifier("middleDev")
    private User firstUser;

    @Override
    public void exec() {

        log.info("========= Execution courseUsers Application: Profile = Dev =========");

        createCollections();

        User fordel = userRepository.findByLogin("ural");
        userRepository.deleteBy_id(fordel.get_id());

        userRepository.deleteByLogin("alexpar");

        log.info("=====================================================");
    }

}
