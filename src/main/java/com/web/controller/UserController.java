package com.web.controller;

import com.web.domain.User;
import com.web.domain.UserRole;
import com.web.repository.UsersRepository;
import com.web.security.CustomUser;
import com.web.security.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private UsersRepository usersRepository;

    List<CustomUser> users=new ArrayList<CustomUser>();

    @GetMapping("/signUp")
    public void signUp(){

    }
    @GetMapping("/list")
    public void list(Model model){
        model.addAttribute("users", usersRepository.findAll());
    }

    @PostMapping("/create")
    public String userCreate(User user){
        customUserDetailService.save(user);
        return "redirect:/";
    }
    @GetMapping("/update/{uNum}")
    public String userUpdate(@PathVariable Long uNum, Model model){
        model.addAttribute("user", usersRepository.findById(uNum).get());
        return "/users/update";
    }
    @PostMapping("/update")
    public String postUpdate(User newUser){
        User user= usersRepository.findById(newUser.getUNum()).get();
        user.setUserName(newUser.getUserName());
        user.setUserPhone(newUser.getUserPhone());
        user.setUserEmail(newUser.getUserEmail());

        usersRepository.save(user);
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
