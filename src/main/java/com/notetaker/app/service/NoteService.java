package com.notetaker.app.service;

import com.notetaker.app.model.Note;
import com.notetaker.app.repository.NoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public Note findByNoteId(long noteId) {
        return noteRepository.findByNoteId(noteId);
    }

    public void saveNote(Note note) {
        noteRepository.save(note);
    }

    @Transactional
    public void deleteByNoteId(long noteId) {noteRepository.deleteByNoteId(noteId);}
}
