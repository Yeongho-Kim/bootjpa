package com.web.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.web.domain.User;
import com.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
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
    @PostMapping("/login")
    public String postLogin(@RequestParam("userId") String userId, @RequestParam("userPw") String userPw,
                            HttpSession session, RedirectAttributes rttr){
        User user=userRepository.findUserByUserId(userId);

        if(user==null){
            rttr.addFlashAttribute("result","아이디가 존재하지 않습니다.");
            return "redirect:/users/login_failed";
        }
        if(!userPw.equals(user.getUserPw())){
            rttr.addFlashAttribute("result","비밀번호가 맞지 않습니다.");
            return "redirect:/users/login_failed";
        }

        session.setAttribute("user",user);
        return "redirect:/";
    }
    @GetMapping("/login_failed")
    public void login_failed(){
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/users/login";
    }

    @GetMapping("/profile")
    public void profile(){

    }


}
