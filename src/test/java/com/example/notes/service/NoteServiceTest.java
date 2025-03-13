package com.example.notes.service;

import com.example.notes.model.Note;
import com.example.notes.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    private Note testNote;

    @BeforeEach
    void setUp() {
        testNote = new Note();
        testNote.setId(1L);
        testNote.setTextHtml("<p>Тестовая заметка</p>");
    }

    @Test
    void testGetAllNotes() {
        List<Note> notes = Arrays.asList(testNote, new Note());
        when(noteRepository.findAll()).thenReturn(notes);

        List<Note> result = noteService.getAllNotes();

        assertEquals(2, result.size());
        verify(noteRepository, times(1)).findAll();
    }

    @Test
    void testGetNoteById() {
        when(noteRepository.findById(1L)).thenReturn(Optional.of(testNote));

        Note result = noteService.getNoteById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("<p>Тестовая заметка</p>", result.getTextHtml());

        verify(noteRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveNote() {
        when(noteRepository.save(any(Note.class))).thenReturn(testNote);

        Note result = noteService.saveNote(testNote);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(noteRepository, times(1)).save(testNote);
    }

    @Test
    void testDeleteNoteById() {
        doNothing().when(noteRepository).deleteById(1L);

        noteService.deleteNoteById(1L);

        verify(noteRepository, times(1)).deleteById(1L);
    }

    @Test
    void testSearchNotes() {
        List<Note> notes = List.of(testNote);
        when(noteRepository.findByTextHtmlContaining("Тест")).thenReturn(notes);

        List<Note> result = noteService.searchNotes("Тест");

        assertEquals(1, result.size());
        assertEquals("<p>Тестовая заметка</p>", result.get(0).getTextHtml());

        verify(noteRepository, times(1)).findByTextHtmlContaining("Тест");
    }
}
