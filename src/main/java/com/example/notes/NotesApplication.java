package com.example.notes;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.notes.model.Note;
import com.example.notes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NotesApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotesApplication.class, args);
    }

    @Autowired
    private NoteService noteService;

    /**
     * Инициализация данных: если заметок нет, создается заметка по умолчанию.
     */
    @Bean
    public CommandLineRunner dataInitializer() {
        return args -> {
            if (noteService.getAllNotes().isEmpty()) {
                Note defaultNote = new Note();
                defaultNote.setText("Добро пожаловать! Это ваша первая заметка.");
                noteService.saveNote(defaultNote);
            }
        };
    }
}
