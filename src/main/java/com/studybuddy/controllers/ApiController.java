package com.studybuddy.controllers;

import com.studybuddy.dto.SessionDTO;
import com.studybuddy.dto.UserDTO;
import com.studybuddy.entity.StudySession;
import com.studybuddy.entity.StudyUser;
import com.studybuddy.repository.StudySessionRepository;
import com.studybuddy.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final StudySessionRepository studySessionRepository;
    private final UserRepository userRepository;

    public ApiController(StudySessionRepository studySessionRepository, UserRepository userRepository) {
        this.studySessionRepository = studySessionRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/sessions")
    public List<SessionDTO> getSessionsForCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        StudyUser user = userRepository.findByUsername(username).orElseThrow();

        return studySessionRepository.findByUser(user).stream()
                .map(s -> new SessionDTO(
                        s.getStartTime(),
                        s.getEndTime(),
                        s.getType(),
                        s.getReflection(),
                        getDurationString(s)
                ))
                .collect(Collectors.toList());
    }

    @GetMapping("/user")
    public UserDTO getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        StudyUser user = userRepository.findByUsername(username).orElseThrow();

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
    }


    private String getDurationString(StudySession s) {
        if (s.getStartTime() == null || s.getEndTime() == null) return "N/A";
        Duration d = Duration.between(s.getStartTime(), s.getEndTime());
        return String.format("%dh %dm", d.toHours(), d.toMinutesPart());
    }
}
