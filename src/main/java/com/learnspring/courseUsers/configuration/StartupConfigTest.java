package com.learnspring.courseUsers.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
@Slf4j
public class StartupConfigTest extends StartupConfig {

    @Override
    public void exec() {
        log.info("========= Execution courseUsers Application: Profile = Test =========");
        clearCollections();
        createCollections();
    }

    private void clearCollections() {
        log.info("Clear collections");
        userRepository.deleteAll();
        courseRepository.deleteAll();
    }
}
