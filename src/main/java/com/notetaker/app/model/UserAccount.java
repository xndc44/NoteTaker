package com.notetaker.app.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "useraccount")
public class UserAccount {
    @Id
    private int id;
    private String username;
    private String password;

}
