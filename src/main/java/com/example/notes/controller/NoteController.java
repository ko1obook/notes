package com.example.notes.controller;

import com.example.notes.model.Note;
import com.example.notes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class NoteController {

    @Autowired
    private NoteService noteService;

    /**
     * Отображение главной страницы со списком заметок.
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("notes", noteService.getAllNotes());
        return "index";
    }

    /**
     * Страница для создания новой заметки.
     */
    @GetMapping("/notes/new")
    public String newNote(Model model) {
        model.addAttribute("note", new Note());
        return "edit";
    }

    /**
     * Сохранение заметки (создание или редактирование).
     */
    @PostMapping("/notes")
    public String saveNote(@ModelAttribute Note note) {
        noteService.saveNote(note);
        return "redirect:/";
    }

    /**
     * Страница редактирования существующей заметки.
     */
    @GetMapping("/notes/edit/{id}")
    public String editNote(@PathVariable Long id, Model model) {
        Note note = noteService.getNoteById(id);
        if (note == null) {
            return "redirect:/";
        }
        model.addAttribute("note", note);
        return "edit";
    }

    /**
     * Удаление заметки по идентификатору.
     */
    @GetMapping("/notes/delete/{id}")
    public String deleteNote(@PathVariable Long id) {
        noteService.deleteNoteById(id);
        return "redirect:/";
    }
}
