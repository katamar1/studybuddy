package com.studybuddy.controllers;

import com.studybuddy.entity.StudySession;
import com.studybuddy.entity.StudyUser;
import com.studybuddy.repository.StudySessionRepository;
import com.studybuddy.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SessionController {

    private final StudySessionRepository studySessionRepository;
    private final UserRepository userRepository;

    public SessionController(StudySessionRepository studySessionRepository, UserRepository userRepository) {
        this.studySessionRepository = studySessionRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/session")
    public String session(Model model) {
        model.addAttribute("sessionForm", new StudySession());
        return "session";
    }

    @PostMapping("/session")
    public String storeSession(@ModelAttribute("sessionForm") StudySession sessionForm) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        StudyUser currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        sessionForm.setUser(currentUser);

        studySessionRepository.save(sessionForm);

        return "redirect:/session";
    }
}
