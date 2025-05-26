package com.studybuddy.controllers;


import com.studybuddy.entity.StudyUser;
import com.studybuddy.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userForm", new StudyUser());
        return "register";
    }

    @PostMapping("/register")
    public String storeUser(@ModelAttribute("userForm") StudyUser userForm) {
        String hashed = passwordEncoder.encode(userForm.getPassword());
        userForm.setPassword(hashed);
        userRepository.save(userForm);
        return "redirect:/home";
    }
}
