package com.studybuddy.controllers;

import com.studybuddy.entity.Club;
import com.studybuddy.entity.StudyUser;
import com.studybuddy.repository.ClubRepository;
import com.studybuddy.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClubController {

    private final ClubRepository clubRepo;
    private final UserRepository userRepo;

    public ClubController(ClubRepository clubRepo, UserRepository userRepo) {
        this.clubRepo = clubRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("/clubs")
    public String clubForm(Model model) {
        StudyUser user = currentUser();
        model.addAttribute("myClubs", user.getClubs());
        return "clubs";
    }

    @PostMapping("/clubs")
    public String addOrJoin(@RequestParam("name") String clubName, Model model) {
        StudyUser user = currentUser();
        clubName = clubName.trim();

        if (clubName.isEmpty()) {
            model.addAttribute("message", "Club name cannot be empty.");
            model.addAttribute("myClubs", user.getClubs());
            return "clubs";
        }

        String finalClubName = clubName;
        Club club = clubRepo.findByName(clubName)
                .orElseGet(() -> clubRepo.save(new Club(finalClubName)));

        if (user.getClubs().add(club)) {
            userRepo.save(user);
            model.addAttribute("message", "You are now a member of \"" + clubName + "\"!");
        } else {
            model.addAttribute("message", "You are already a member of \"" + clubName + "\".");
        }

        model.addAttribute("myClubs", user.getClubs());
        return "clubs";
    }

    private StudyUser currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepo.findByUsername(auth.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
