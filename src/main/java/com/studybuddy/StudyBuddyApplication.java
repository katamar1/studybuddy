package com.studybuddy;

import com.studybuddy.entity.StudyUser;
import com.studybuddy.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class StudyBuddyApplication {

    private static final Logger log = LoggerFactory.getLogger(StudyBuddyApplication.class);

    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    public static void main(String[] args) {

        SpringApplication.run(StudyBuddyApplication.class, args);
    }

}