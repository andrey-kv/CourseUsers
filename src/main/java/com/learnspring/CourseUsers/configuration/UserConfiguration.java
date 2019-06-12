package com.learnspring.CourseUsers.configuration;

import com.learnspring.CourseUsers.model.JobPosition;
import com.learnspring.CourseUsers.model.Level;
import com.learnspring.CourseUsers.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class UserConfiguration {

    @Bean
    @Qualifier("middleDev")
    @Scope("prototype")
    public User getUser() {
        User user = new User();
        user.setJobPosition(JobPosition.SOFTWARE_ENGINEER);
        user.setLevel(Level.L2_MIDDLE);
        return user;
    }
}
