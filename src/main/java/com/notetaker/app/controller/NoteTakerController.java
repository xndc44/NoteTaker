package com.notetaker.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class NoteTakerController {


    @RequestMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/success")
    public String success() {
        return "success";
    }
}
