package com.studybuddy.controllers;

import com.studybuddy.entity.StudyUser;
import com.studybuddy.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    private final UserRepository userRepository;

    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("userForm", new StudyUser());
        return "home";
    }

    @PostMapping("/home")
    public String storeUser(@ModelAttribute("userForm") StudyUser userForm) {
        userRepository.save(userForm);
        return "redirect:/home";
    }
}
