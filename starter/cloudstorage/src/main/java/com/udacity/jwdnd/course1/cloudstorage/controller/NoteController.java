package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.UserNote;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/note")
public class NoteController {
    private final UserService userService;
    private final NoteService noteService;

    public NoteController(NoteService noteService, UserService userService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @PostMapping("/add")
    public String addNote(@ModelAttribute UserNote note, Authentication authentication, RedirectAttributes redirectAttributes) throws IOException {
        if (note.getNoteId() != null) {
            this.noteService.updateNote(note);
        }
        else {
            int userId = this.userService.getUser(authentication.getName()).getUserId();
            note.setUserId(userId);
            this.noteService.addNote(note);
        }
        return "redirect:/home";
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable Integer noteId) {
        this.noteService.deleteNote(noteId);
        return "redirect:/home";
    }

}
