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

    @Bean
    public CommandLineRunner demo(UserRepository repository) {
        return (args) -> {
            // save a few customers
            repository.save(new StudyUser("Jack", "Bauer", "jb"));
            repository.save(new StudyUser("Chloe", "O'Brian", "co"));
            repository.save(new StudyUser("Kim", "Bauer", "kb"));
            repository.save(new StudyUser("David", "Palmer", "dp"));
            repository.save(new StudyUser("Michelle", "Dessler", "md"));

            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            repository.findAll().forEach(customer -> {
                log.info(customer.toString());
            });
            log.info("");

            // fetch an individual customer by ID
            StudyUser customer = repository.findById(1L);
            log.info("Customer found with findById(1L):");
            log.info("--------------------------------");
            log.info(customer.toString());
            log.info("");

            // fetch customers by last name
            log.info("Customer found with findByLastName('Bauer'):");
            log.info("--------------------------------------------");
            repository.findByLastName("Bauer").forEach(bauer -> {
                log.info(bauer.toString());
            });
            log.info("");
        };
    }
}