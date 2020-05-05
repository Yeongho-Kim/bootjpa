package com.web;

import com.web.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BootjpaApplicationTests {
    @Autowired
    private UsersRepository usersRepository;
    @Test
    void contextLoads() {
    }
}
