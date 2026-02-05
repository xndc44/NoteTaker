package com.notetaker.app.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "notepad")
@Data
public class Notepad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notepadId;

    @OneToOne
    @JoinColumn(name = "userId")
    private UserAccount owner;

    @OneToMany(mappedBy = "notepad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes = new ArrayList<>();
}
