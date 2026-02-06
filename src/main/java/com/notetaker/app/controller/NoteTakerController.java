package com.notetaker.app.controller;

import com.notetaker.app.model.Note;
import com.notetaker.app.model.Notepad;
import com.notetaker.app.model.UserPrincipal;
import com.notetaker.app.service.NoteService;
import com.notetaker.app.service.NotepadService;
import com.notetaker.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;


@Controller
public class NoteTakerController {

    @Autowired
    UserService userService;
    @Autowired
    NoteService noteService;
    @Autowired
    private NotepadService notepadService;

    @GetMapping("/")
    public String redirectHome() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login(@AuthenticationPrincipal UserPrincipal principal) {
        if (principal != null) {
            return "redirect:/notepad"; // already logged in
        }
        return "login"; // show login page
    }


    @GetMapping("/register")
    public String showRegistrationForm(@AuthenticationPrincipal UserPrincipal principal) {
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
    public String notepad(Model model, @AuthenticationPrincipal UserPrincipal principal) {
        Notepad notepad = notepadService.findByNotepadId(principal.getNotepadId());
        model.addAttribute("username", principal.getUsername());
        model.addAttribute("notes", notepad.getNotes());
        return "notepad";
    }

    @PostMapping("/notepad")
    public String createNote(Model model, @RequestParam String title, @AuthenticationPrincipal UserPrincipal principal, RedirectAttributes redirectAttributes) {
        Notepad notepad = notepadService.findByNotepadId(principal.getNotepadId());
        Note note = new Note();
        note.setTitle(title);
        note.setNotepad(notepad);
        noteService.saveNote(note);
        model.addAttribute("username", principal.getUsername());
        model.addAttribute("notes", notepad.getNotes());
        redirectAttributes.addFlashAttribute("success", "Note created successfully!");
        return "redirect:/notepad";
    }

    @GetMapping("/notepad/{id}")
    public String getNote(@PathVariable Long id, Model model, @AuthenticationPrincipal UserPrincipal principal) {
        Note note = noteService.findByNoteId(id);
        model.addAttribute("note", note);
        model.addAttribute("username", principal.getUsername());
        return "note";
    }

    @DeleteMapping("/notepad/{id}")
    public String deleteNote(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal principal,
            RedirectAttributes redirectAttributes) {

        Notepad notepad = notepadService.findByNotepadId(principal.getNotepadId());
        if (notepad == null) {
            redirectAttributes.addFlashAttribute("error", "Notepad not found!");
            return "redirect:/notepad";
        }

        boolean removedFromList = notepad.getNotes().removeIf(note -> note.getNoteId() == id);
        noteService.deleteByNoteId(id);

        if (removedFromList) {
            redirectAttributes.addFlashAttribute("success", "Note deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("warning", "Note was not found in your notepad, but deletion attempted.");
        }

        return "redirect:/notepad";
    }


    @PostMapping("/notepad/{id}")
    public String saveNote(@PathVariable Long id, @RequestParam String title,
                           @RequestParam String content, RedirectAttributes redirectAttributes,
                           @AuthenticationPrincipal UserPrincipal principal) {
        Note note = noteService.findByNoteId(id);
        note.setTitle(title);
        note.setContent(content);
        note.setNotepad(notepadService.findByNotepadId(principal.getNotepadId()));
        noteService.saveNote(note);

        redirectAttributes.addFlashAttribute("message", "Note saved successfully!");
        return "redirect:/notepad/" + id;
    }

}
