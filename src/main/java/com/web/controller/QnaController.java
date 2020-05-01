package com.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/qna")
public class QnaController {
    @GetMapping("/form")
    public void form(){

    }
    @GetMapping("/show")
    public void show(){

    }

    @GetMapping("/list")
    public void list(){

    }
}
