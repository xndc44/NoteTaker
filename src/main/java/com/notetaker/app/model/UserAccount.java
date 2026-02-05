package com.notetaker.app.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "useraccount")
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String username;
    private String password;

    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL)
    private Notepad notepad;
}
