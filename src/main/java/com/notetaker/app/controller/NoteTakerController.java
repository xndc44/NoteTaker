package com.notetaker.app.controller;

import com.notetaker.app.model.UserAccount;
import com.notetaker.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NoteTakerController {

    @Autowired
    UserService userService;


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password,
                               RedirectAttributes redirectAttributes) {

        // Check if user already exists

        try {
            userService.loadUserByUsername(username);
            redirectAttributes.addFlashAttribute("error", "Username is already taken!");
            return "redirect:/register";

        } catch (UsernameNotFoundException e) {

            userService.register(username, password);

            return "redirect:/login?success";
        }

    }

    @GetMapping("/notepad")
    public String notepad() {
        return "notepad";
    }

    @GetMapping("/success")
    public String success() {
        return "success";
    }
}
