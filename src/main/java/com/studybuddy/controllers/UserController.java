package com.studybuddy.controllers;

import com.studybuddy.dto.UserJoinFormDTO;
import com.studybuddy.entity.Club;
import com.studybuddy.entity.Course;
import com.studybuddy.entity.StudyUser;
import com.studybuddy.repository.ClubRepository;
import com.studybuddy.repository.CourseRepository;
import com.studybuddy.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {
    private final UserRepository userRepository;
    private final ClubRepository clubRepository;
    private final CourseRepository courseRepository;

    public UserController(UserRepository userRepository, ClubRepository clubRepository, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.clubRepository = clubRepository;
        this.courseRepository = courseRepository;
    }

    @GetMapping("/join")
    public String showJoinForm(Model model) {
        model.addAttribute("userJoinForm", new UserJoinFormDTO());
        model.addAttribute("allClubs", clubRepository.findAll());
        model.addAttribute("allCourses", courseRepository.findAll());
        return "join";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute UserJoinFormDTO userJoinForm) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        StudyUser user = userRepository.findByUsername(username).orElseThrow();

        List<Club> clubs = clubRepository.findAllById(userJoinForm.getClubIds());
        List<Course> courses = courseRepository.findAllById(userJoinForm.getCourseIds());

        user.getClubs().clear();
        user.getClubs().addAll(clubs);
        user.getCourses().clear();
        user.getCourses().addAll(courses);

        userRepository.save(user);
        return "redirect:/dashboard";
    }
}
