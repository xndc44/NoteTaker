package com.notetaker.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
