package com.web.controller;

import com.web.domain.User;
import com.web.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private UsersRepository usersRepository;

    private List<User> users=new ArrayList<User>();
    @GetMapping("/")
    public String index(){
        return "/index";
    }
    @GetMapping("/accessDenied")
    public void accessDenied(){
    }
}
