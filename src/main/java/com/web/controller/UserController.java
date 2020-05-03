package com.web.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.web.domain.User;
import com.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
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
        return "redirect:/users/list";
    }
    @GetMapping("/update/{uNum}")
    public String userUpdate(@PathVariable Long uNum, Model model){
        model.addAttribute("user",userRepository.findById(uNum).get());
        return "/users/update";
    }
    @PostMapping("/update")
    public String postUpdate(User newUser){
        User user=userRepository.findById(newUser.getUNum()).get();
        user.setUserName(newUser.getUserName());
        user.setUserPhone(newUser.getUserPhone());
        user.setUserEmail(newUser.getUserEmail());
        userRepository.save(user);
        return "redirect:/users/list";
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
