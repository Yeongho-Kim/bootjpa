package com.web.controller;

import com.web.domain.User;
import com.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    List<User> users=new ArrayList<User>();

    @GetMapping("/form")
    public void form(){

    }
    @GetMapping("/list")
    public void list(Model model){
        model.addAttribute("users",userRepository.findAll());
        System.out.println(users.toString());
    }
    @PostMapping("/create")
    public String userCreate(User user){
        userRepository.save(user);
        return "redirect:/user/list";
    }
    @GetMapping("/login")
    public void login(){

    }
    @GetMapping("/login_failed")
    public void login_failed(){

    }
    @GetMapping("/profile")
    public void profile(){

    }


}
