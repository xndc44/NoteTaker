package com.notetaker.app.repository;

import com.notetaker.app.model.Note;
import com.notetaker.app.model.Notepad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotepadRepository extends JpaRepository<Notepad, Long> {

    Notepad findByNotepadId(Long notepadId);

}
