package com.studybuddy;

import com.studybuddy.entity.StudySession;
import com.studybuddy.entity.StudyUser;
import com.studybuddy.repository.StudySessionRepository;
import com.studybuddy.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class StudySessionRepositoryTest {

    @Autowired
    private StudySessionRepository studySessionRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveSession() {
        StudyUser user = new StudyUser();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setEmail("testuser@example.com");
        userRepository.save(user);

        StudySession session = new StudySession();
        session.setStartTime(LocalDateTime.now().minusHours(2));
        session.setEndTime(LocalDateTime.now());
        session.setType("Reading");
        session.setReflection("Studied JPA relations.");
        session.setUser(user);

        StudySession savedSession = studySessionRepository.save(session);

        assertThat(savedSession.getId()).isNotNull();
        assertThat(savedSession.getUser().getUsername()).isEqualTo("testuser");
        assertThat(savedSession.getReflection()).isEqualTo("Studied JPA relations.");
    }
}
