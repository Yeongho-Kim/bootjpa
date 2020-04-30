package com.web.controller;

import com.web.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SampleController {
    private List<User> users=new ArrayList<User>();
    @GetMapping("/index")
    public void index(){
    }
    @GetMapping("/form")
    public void form(){
    }
    @GetMapping("/create")
    public void getCreate(User user) {
        System.out.println(user.toString());

    }
    @PostMapping("/create")
    public String postCreate(User user){
        System.out.println(user.toString());
        users.add(user);
        System.out.println(users.toString());
        return "redirect:list";
    }

    @GetMapping("/list")
    public String list(Model model){

        model.addAttribute("users",users);
        System.out.println(users.toString());
        return "/list";
    }
}
