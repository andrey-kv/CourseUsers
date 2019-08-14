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
    private StartupConfig startupConfig;

    @EventListener(ApplicationReadyEvent.class)
    private void exec() {
        startupConfig.exec();
    }

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
