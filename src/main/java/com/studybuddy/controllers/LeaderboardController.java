package com.studybuddy.controllers;

import com.studybuddy.dto.LeaderboardEntryDTO;
import com.studybuddy.repository.StudySessionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LeaderboardController {

    private final StudySessionRepository studySessionRepository;

    public LeaderboardController(StudySessionRepository studySessionRepository) {
        this.studySessionRepository = studySessionRepository;
    }

    @GetMapping("/leaderboard")
    public String leaderboard(Model model) {
        List<LeaderboardEntryDTO> leaderboard = studySessionRepository.getRawLeaderboard();
        model.addAttribute("leaderboard", leaderboard);
        return "leaderboard";
    }
}

