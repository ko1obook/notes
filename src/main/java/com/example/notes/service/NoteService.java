package com.example.notes.service;

import com.example.notes.model.Note;
import com.example.notes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    /**
     * Получить список всех заметок.
     */
    public List<Note> getAllNotes() {
        return (List<Note>) noteRepository.findAll();
    }

    /**
     * Метод для поиска по ключевому слову
     */
    public List<Note> searchNotes(String keyword) {
        return noteRepository.findByTextContaining(keyword);
    }

    /**
     * Получить заметку по идентификатору.
     */
    public Note getNoteById(Long id) {
        return noteRepository.findById(id).orElse(null);
    }

    /**
     * Сохранить заметку (новую или обновленную).
     */
    public Note saveNote(Note note) {
        return noteRepository.save(note);
    }

    /**
     * Удалить заметку по идентификатору.
     */
    public void deleteNoteById(Long id) {
        noteRepository.deleteById(id);
    }
}
