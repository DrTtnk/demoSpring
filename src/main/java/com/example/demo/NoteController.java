package com.example.demo;

import org.springframework.web.bind.annotation.*;

class NotNotFoundException extends RuntimeException {
    NotNotFoundException(String message) {
        super(message);
    }
}

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

    @GetMapping("/notes/{id}")
    public Note getNote(@PathVariable Long id) {
        var note = noteRepository.findById(id);
        if (note.isEmpty()) throw new NotNotFoundException("Note not found");
        return note.get();
    }

    @PostMapping("/notes/create")
    public Note createNote(@RequestBody Note note) {
        note.title = note.title.toUpperCase();
        return noteRepository.save(note);
    }
}
