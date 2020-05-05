package com.web.controller;

import com.web.domain.User;
import com.web.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
