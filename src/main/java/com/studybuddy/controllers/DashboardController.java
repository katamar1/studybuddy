package com.studybuddy.controllers;

import com.studybuddy.entity.StudySession;
import com.studybuddy.entity.StudyUser;
import com.studybuddy.repository.StudySessionRepository;
import com.studybuddy.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    private final StudySessionRepository studySessionRepository;
    private final UserRepository userRepository;

    public DashboardController(StudySessionRepository studySessionRepository, UserRepository userRepository) {
        this.studySessionRepository = studySessionRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        StudyUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<StudySession> sessions = studySessionRepository.findByUser(user);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm");

        List<Map<String, Object>> sessionData = sessions.stream().map(s -> {
            Map<String, Object> data = new HashMap<>();
            data.put("start", s.getStartTime() != null ? s.getStartTime().format(formatter) : "N/A");
            data.put("end", s.getEndTime() != null ? s.getEndTime().format(formatter) : "N/A");
            data.put("type", s.getType());
            data.put("reflection", s.getReflection());
            data.put("usedAI", s.isUsedAI());

            if (s.getStartTime() != null && s.getEndTime() != null) {
                long mins = Duration.between(s.getStartTime(), s.getEndTime()).toMinutes();
                data.put("duration", String.format("%dh %dm", mins / 60, mins % 60));
            } else {
                data.put("duration", "N/A");
            }

            return data;
        }).collect(Collectors.toList());

        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        long totalMinutes = sessions.stream()
                .filter(s -> s.getStartTime() != null && s.getEndTime() != null)
                .filter(s -> s.getStartTime().isAfter(sevenDaysAgo))
                .mapToLong(s -> Duration.between(s.getStartTime(), s.getEndTime()).toMinutes())
                .sum();

        String totalTime = String.format("%d hours %d minutes", totalMinutes / 60, totalMinutes % 60);

        model.addAttribute("username", user.getUsername());
        model.addAttribute("sessionList", sessionData);
        model.addAttribute("totalTime", totalTime);

        model.addAttribute("clubs", user.getClubs());
        model.addAttribute("classes", user.getCourses());
        model.addAttribute("anonymous", user.isAnonymous());


        return "dashboard";
    }

    @PostMapping("/toggle-anonymous")
    public String toggleAnonymous(@RequestParam(value = "anonymous", required = false) String anonymous) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        StudyUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setAnonymous(anonymous != null);
        userRepository.save(user);

        return "redirect:/dashboard";
    }

}
