package com.example.demo;

import org.springframework.web.bind.annotation.*;

@RestController
public class NoteController {

    private final NoteRepository noteRepository;

    public NoteController(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @GetMapping("/notes")
    public Iterable<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    @PostMapping("/createNote")
    public Note createNote(@RequestBody Note note) {
        note.title = note.title.toUpperCase();
        return noteRepository.save(note);
    }
}
