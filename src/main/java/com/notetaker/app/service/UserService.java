package com.notetaker.app.service;

import com.notetaker.app.model.Notepad;
import com.notetaker.app.model.UserAccount;
import com.notetaker.app.model.UserPrincipal;
import com.notetaker.app.repository.NotepadRepository;
import com.notetaker.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotepadRepository notepadRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAccount> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("username: " + username);
        }

        return new UserPrincipal(user.get());
    }

    public void register(String username, String password) {
        UserAccount user = new UserAccount();
        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        Notepad notepad = new Notepad();
        notepad.setOwner(user);
        user.setNotepad(notepad);
        userRepository.save(user);
        notepadRepository.save(notepad);
    }
}
