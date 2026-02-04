package com.notetaker.app.repository;

import com.notetaker.app.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserAccount, Integer> {

    UserAccount findByUsername(String username);
}
