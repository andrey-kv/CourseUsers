package com.learnspring.courseUsers.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

@Slf4j
public class DatabasePreparer implements TestExecutionListener {

    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {

        log.info("= Start of tests execution ============================");

//        UserRepository userRepository = (UserRepository) testContext.getApplicationContext().getBean("userRepository");
//        CourseRepository courseRepository = (CourseRepository) testContext.getApplicationContext().getBean("courseRepository");
//
//        createUsers(userRepository);
//        createCourses(courseRepository);
    }

    @Override
    public void afterTestClass(TestContext testContext) throws Exception {
        log.info("= End of tests execution ============================");

//        UserRepository userRepository = (UserRepository) testContext.getApplicationContext().getBean("userRepository");
//        CourseRepository courseRepository = (CourseRepository) testContext.getApplicationContext().getBean("courseRepository");
//
//        userRepository.deleteAll();
//        courseRepository.deleteAll();
    }

}
