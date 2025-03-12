package com.example.notes.repository;

import com.example.notes.model.Note;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {

    // Метод для поиска заметок по содержимому (например, по ключевому слову)
    List<Note> findByTextHtmlContaining(String keyword);
}
