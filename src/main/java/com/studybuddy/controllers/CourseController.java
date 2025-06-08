package com.studybuddy.controllers;

import com.studybuddy.entity.Course;
import com.studybuddy.entity.StudyUser;
import com.studybuddy.repository.CourseRepository;
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
public class CourseController {

    private final CourseRepository courseRepo;
    private final UserRepository   userRepo;

    public CourseController(CourseRepository courseRepo, UserRepository userRepo) {
        this.courseRepo = courseRepo;
        this.userRepo   = userRepo;
    }

    private StudyUser currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepo.findByUsername(auth.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @GetMapping("/courses")
    public String showCourses(Model model) {
        StudyUser user = currentUser();
        model.addAttribute("myCourses", user.getCourses());
        return "courses";
    }

    @PostMapping("/courses")
    public String addCourse(@RequestParam("name") String courseName, Model model) {

        StudyUser user = currentUser();
        courseName = courseName.trim();

        if (courseName.isBlank()) {
            model.addAttribute("message", "Course name can’t be empty.");
            model.addAttribute("myCourses", user.getCourses());
            return "courses";
        }

        String finalCourseName = courseName;
        Course course = courseRepo.findByName(courseName)
                .orElseGet(() -> courseRepo.save(new Course(finalCourseName)));

        if (user.getCourses().add(course)) {
            userRepo.save(user);
            model.addAttribute("message", "Added \"" + courseName + "\" to your list.");
        } else {
            model.addAttribute("message", "You already have \"" + courseName + "\".");
        }

        model.addAttribute("myCourses", user.getCourses());
        return "courses";
    }
}
