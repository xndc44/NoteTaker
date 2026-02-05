package com.notetaker.app.service;

import com.notetaker.app.model.Note;
import com.notetaker.app.model.Notepad;
import com.notetaker.app.repository.NoteRepository;
import com.notetaker.app.repository.NotepadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class NotepadService {

    @Autowired
    private NotepadRepository notepadRepository;

    public Notepad findByNotepadId(long notepadId) {
        return notepadRepository.findByNotepadId(notepadId);
    }
}
