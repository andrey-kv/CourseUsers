package com.learnspring.courseUsers.service;

import com.learnspring.courseUsers.configuration.StartupConfig;
import com.learnspring.courseUsers.model.*;
import com.learnspring.courseUsers.repository.CourseRepository;
import com.learnspring.courseUsers.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Autowired
    private StartupConfig startupConfig;

    @EventListener(ApplicationReadyEvent.class)
    private void exec() {
        startupConfig.exec();
    }

//    private void createUsers() {
//        log.info("Creating Users.");
//        log.info("Creating Users.");
//        List<User> users = new ArrayList<>();
//        users.add(new User("Andrii", "andrii@gmail.com", "Andrii", "Lozhkin",
//                Status.ACTIVE, JobPosition.QA_ENGINEER, Level.MIDDLE, LocalDate.parse("1989-02-20")));
//        users.add(new User("maksym", "maksim@gmail.com", "Maksym", "Vilkin",
//                Status.CREATED, JobPosition.SOFTWARE_ENGINEER, Level.MIDDLE, LocalDate.parse("1990-03-16")));
//        users.add(new User("ural", "afterpaty@gmail.com", "Sergiy", "Uralov",
//                Status.CREATED, JobPosition.NETWORK_ENGINEER, Level.SENIOR, LocalDate.parse("1985-04-26")));
//        users.add(new User("alexpar", "alexpar@gmail.com", "Oleksiy", "Parmezanenko",
//                Status.APPROVED, JobPosition.SOFTWARE_ENGINEER, Level.SENIOR, LocalDate.parse("1973-07-13")));
//        users.add(new User("tormoz", "taranenko@gmail.com", "Vitalii", "Manko",
//                Status.APPROVED, JobPosition.SYSTEMS_ANALYST, Level.MIDDLE, LocalDate.parse("1988-10-10")));
//        users.add(new User("usr01", "a.usr01@gmail.com", "Taras", "Andrienko",
//                Status.ACTIVE, JobPosition.QA_ENGINEER, Level.TRAINEE, LocalDate.parse("1990-03-03")));
//
//        User usr = new User("addr", "addr_has@gmail.com",
//                "Mykola", "Lopar", Status.ACTIVE, JobPosition.SOFTWARE_ENGINEER, Level.JUNIOR, LocalDate.parse("1990-05-07"));
//        Address adr = new Address("Ukraine", "Lviv", "79019");
//        usr.setAddress(adr);
//        users.add(usr);
//
//        for (User user : users) {
//            if (userRepository.findByLogin(user.getLogin()) == null) {
//                userRepository.save(user);
//                log.info("Save User = " + user.toString());
//            }
//        }
//    }
//
//    private void createCourses() {
//        log.info("Creating Courses.");
//        List<Course> courses = new ArrayList<>();
//        courses.add(new Course("Angular 6.0", Level.MIDDLE, 22, CourseType.PERSON, 4.7));
//        courses.add(new Course("Spring Cloud", Level.SENIOR, 14, CourseType.PERSON, 4.6));
//        courses.add(new Course("What's new in Java 12", Level.JUNIOR, 8, CourseType.ONLINE, 4.2));
//        courses.add(new Course("Streams in Java 8", Level.MIDDLE, 8, CourseType.ONLINE, 4.4));
//        courses.add(new Course("Docker configuration", Level.MIDDLE, 6, CourseType.OTHER, 4.6));
//        courses.add(new Course("Deploing applications to AWS", Level.MIDDLE, 16, CourseType.ONLINE, 4.9));
//
//        for (Course course : courses) {
//            if (courseRepository.findByName(course.getName()) == null) {
//                courseRepository.save(course);
//                log.info("Save Course = " + course.toString());
//            }
//        }
//
//    }

    private void showPagination() {

        log.info("=================== Pagination ======================");
        Pageable pageable = PageRequest.of(0, 2, Sort.by("login").descending());

        while(true){
            Page<User> page = userRepository.getUsersByCity("Lviv", pageable);
            List<User> list = page.getContent();
            displayList(list, "Page no: "+page.getNumber());
            if(!page.hasNext()){
                break;
            }
            pageable = page.nextPageable();
        }
        log.info("=====================================================");
    }

    private <T> void displayList(List<T> items, String info) {
        if (items != null && items.size() > 0) {
            log.info(info);
            for (T item : items ) {
                log.info(item.toString());
            }
        }
    }
}
