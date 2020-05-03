package com.web;

import com.web.domain.User;
import com.web.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BootjpaApplicationTests {
    @Autowired
    private UserRepository userRepository;
    @Test
    void contextLoads() {
    }
}
