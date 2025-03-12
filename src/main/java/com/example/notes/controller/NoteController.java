package com.example.notes.controller;

import com.example.notes.model.Note;
import com.example.notes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    /**
     * Отображение списка заметок или поиск по ключевому слову.
     */
    @GetMapping
    public String getNotes(Model model, @RequestParam(required = false) String keyword) {
        List<Note> notes;
        if (keyword != null && !keyword.isEmpty()) {
            notes = noteService.searchNotes(keyword);  // Если ключевое слово передано, ищем заметки
        } else {
            notes = noteService.getAllNotes();  // В противном случае показываем все заметки
        }
        model.addAttribute("notes", notes);
        model.addAttribute("keyword", keyword);  // Сохраняем значение поиска для отображения в форме
        return "index";  // имя вашего шаблона
    }

    /**
     * Страница для создания новой заметки.
     */
    @GetMapping("/new")
    public String newNote(Model model) {
        model.addAttribute("note", new Note());
        return "edit";
    }

    /**
     * Сохранение заметки (создание или редактирование).
     */
    @PostMapping
    public String saveNote(@ModelAttribute Note note) {
        noteService.saveNote(note);
        return "redirect:/notes";  // После сохранения редирект на страницу списка
    }

    /**
     * Страница редактирования существующей заметки.
     */
    @GetMapping("/edit/{id}")
    public String editNote(@PathVariable Long id, Model model) {
        Note note = noteService.getNoteById(id);
        if (note == null) {
            return "redirect:/notes";  // Если заметки нет, перенаправляем на список
        }
        model.addAttribute("note", note);
        return "edit";
    }

    /**
     * Удаление заметки по идентификатору.
     */
    @GetMapping("/delete/{id}")
    public String deleteNote(@PathVariable Long id) {
        noteService.deleteNoteById(id);
        return "redirect:/notes";  // После удаления редирект на список
    }
}
