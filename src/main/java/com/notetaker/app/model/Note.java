package com.notetaker.app.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long noteId;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "notepadId")
    private Notepad notepad;

}
