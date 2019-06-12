package com.learnspring.courseUsers.repository;

import com.learnspring.courseUsers.model.Address;
import com.learnspring.courseUsers.model.Level;
import com.learnspring.courseUsers.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface UserRepositoryCustom {
    void updateNameByLogin(String login, String newFirstName);
    void updateNameByLogin(String login, String newFirstName, String newLastName);
    List<User> findUsersWhenLevelGreaterThen(Level level);

    void updateAddressByLogin(String login, Address address);

    List<User> findUsersByCity(String city);
    Page<User> getUsersByCity(String city, Pageable pageable);

    void approveActiveUsers();

    List<User> findSpecialQuery(int age, String city);
    List<User> findSpecialBasicQuery(int age, String city);
}
