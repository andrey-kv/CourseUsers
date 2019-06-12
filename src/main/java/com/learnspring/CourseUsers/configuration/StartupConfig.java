package com.learnspring.CourseUsers.configuration;

import com.learnspring.CourseUsers.model.*;
import com.learnspring.CourseUsers.repository.CourseRepository;
import com.learnspring.CourseUsers.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@NoArgsConstructor
public abstract class StartupConfig {

    protected UserRepository userRepository;
    protected CourseRepository courseRepository;

    @Autowired
    public final void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public final void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public abstract void exec();

    protected void createCollections() {
        createUsers();
        createCourses();
    }

    private void createUsers() {

        log.info("Creating Users.");
        log.info("Creating Users.");
        List<User> users = new ArrayList<>();
        users.add(new User("Andrii", "andrii@gmail.com", "Andrii", "Lozhkin",
                Status.ACTIVE, JobPosition.QA_ENGINEER, Level.L2_MIDDLE, LocalDate.parse("1989-02-20")));
        users.add(new User("maksym", "maksim@gmail.com", "Maksym", "Vilkin",
                Status.CREATED, JobPosition.SOFTWARE_ENGINEER, Level.L2_MIDDLE, LocalDate.parse("1990-03-16")));
        users.add(new User("ural", "afterpaty@gmail.com", "Sergiy", "Uralov",
                Status.CREATED, JobPosition.NETWORK_ENGINEER, Level.L3_SENIOR, LocalDate.parse("1985-04-26")));
        users.add(new User("alexpar", "alexpar@gmail.com", "Oleksiy", "Parmezanenko",
                Status.APPROVED, JobPosition.SOFTWARE_ENGINEER, Level.L3_SENIOR, LocalDate.parse("1973-07-13")));
        users.add(new User("tormoz", "taranenko@gmail.com", "Vitalii", "Manko",
                Status.APPROVED, JobPosition.SYSTEMS_ANALYST, Level.L2_MIDDLE, LocalDate.parse("1988-10-10")));
        users.add(new User("usr01", "a.usr01@gmail.com", "Taras", "Andrienko",
                Status.ACTIVE, JobPosition.QA_ENGINEER, Level.L0_TRAINEE, LocalDate.parse("1990-03-03")));

        User usr = new User("addr", "addr_has@gmail.com",
                "Mykola", "Lopar", Status.ACTIVE, JobPosition.SOFTWARE_ENGINEER, Level.L1_JUNIOR, LocalDate.parse("1990-05-07"));
        Address adr = new Address("Ukraine", "Lviv", "79019");
        usr.setAddress(adr);
        users.add(usr);

        for (User user : users) {
            if (userRepository.findByLogin(user.getLogin()) == null) {
                userRepository.save(user);
                log.info("Save User = " + user.toString());
            }
        }

        userRepository.updateAddressByLogin("tormoz", new Address("Ukraine", "Lviv", "79019"));
        userRepository.updateAddressByLogin("ural", new Address("Ukraine", "Lviv", "79037"));
        userRepository.updateAddressByLogin("Andrii", new Address("Ukraine", "Kyiv", "02028"));
        userRepository.updateAddressByLogin("maksym", new Address("Ukraine", "Lviv", "79019"));
    }

    private void createCourses() {
        log.info("Creating Courses.");
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("Angular 6.0", Level.L2_MIDDLE, 22, CourseType.PERSON, 4.7));
        courses.add(new Course("Spring Cloud", Level.L3_SENIOR, 14, CourseType.PERSON, 4.6));
        courses.add(new Course("What's new in Java 12", Level.L1_JUNIOR, 8, CourseType.ONLINE, 4.2));
        courses.add(new Course("Streams in Java 8", Level.L2_MIDDLE, 8, CourseType.ONLINE, 4.4));
        courses.add(new Course("Docker configuration", Level.L2_MIDDLE, 6, CourseType.OTHER, 4.6));
        courses.add(new Course("Deploing applications to AWS", Level.L2_MIDDLE, 16, CourseType.ONLINE, 4.9));
        courses.add(new Course("First steps into IT", Level.L0_TRAINEE, 10, CourseType.OTHER, 4.0));

        for (Course course : courses) {
            if (courseRepository.findByName(course.getName()) == null) {
                courseRepository.save(course);
                log.info("Save Course = " + course.toString());
            }
        }
    }

}
