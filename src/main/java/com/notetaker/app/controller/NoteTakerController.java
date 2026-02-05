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
    public String notepad(Model model, @AuthenticationPrincipal UserPrincipal principal) {
        Notepad notepad = notepadService.findByNotepadId(principal.getNotepadId());
        model.addAttribute("username", principal.getUsername());
        model.addAttribute("notes", notepad.getNotes());
        return "notepad";
    }

    @PostMapping("/notepad")
    public String createNote(Model model, @RequestParam String title, @AuthenticationPrincipal UserPrincipal principal) {
        Notepad notepad = notepadService.findByNotepadId(principal.getNotepadId());
        Note note = new Note();
        note.setTitle(title);
        note.setNotepad(notepad);
        noteService.saveNote(note);
        model.addAttribute("username", principal.getUsername());
        model.addAttribute("notes", notepad.getNotes());
        return "redirect:/notepad";
    }

    @GetMapping("/notepad/{id}")
    public String editNote(@PathVariable Long id, Model model, @AuthenticationPrincipal UserPrincipal principal) {
        Note note = noteService.findByNoteId(id);
        model.addAttribute("note", note);
        model.addAttribute("username", principal.getUsername());
        return "note";
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
