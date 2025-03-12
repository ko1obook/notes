package com.example.notes.controller;

import com.example.notes.model.Note;
import com.example.notes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
            notes = noteService.searchNotes(keyword);
        } else {
            notes = noteService.getAllNotes();
        }
        model.addAttribute("notes", notes);
        model.addAttribute("keyword", keyword);
        return "index";
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
        return "redirect:/notes";
    }

    /**
     * Страница редактирования существующей заметки.
     */
    @GetMapping("/edit/{id}")
    public String editNote(@PathVariable Long id, Model model) {
        Note note = noteService.getNoteById(id);
        if (note == null) {
            return "redirect:/notes";
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
        return "redirect:/notes";
    }

    /**
     * Загрузка изображения: сохраняем файл в /uploads/ и возвращаем URL.
     */
    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // Генерируем уникальное имя файла
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            // Указываем директорию для загрузки
            Path uploadPath = Paths.get("uploads");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Сохраняем файл
            Path filePath = uploadPath.resolve(fileName);
            Files.write(filePath, file.getBytes());

            // Возвращаем URL изображения
            return ResponseEntity.ok(Map.of("url", "/uploads/" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading image");
        }
    }
}
