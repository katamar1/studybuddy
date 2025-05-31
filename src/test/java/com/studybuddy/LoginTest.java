package com.studybuddy;

import com.studybuddy.entity.StudyUser;
import com.studybuddy.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LoginTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        StudyUser user = new StudyUser();
        user.setUsername("loginuser");
        user.setPassword(passwordEncoder.encode("password123"));
        user.setEmail("loginuser@example.com");
        userRepository.save(user);
    }




    @Test
    void testSuccessfulLogin() throws Exception {
        mockMvc.perform(formLogin().user("loginuser").password("password123"))
                .andExpect(authenticated());
    }

    @Test
    void testFailedLogin() throws Exception {
        mockMvc.perform(formLogin().user("loginuser").password("wrongpassword"))
                .andExpect(unauthenticated());
    }
}
