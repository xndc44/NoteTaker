package com.notetaker.app.service;

import com.notetaker.app.model.UserAccount;
import com.notetaker.app.model.UserPrincipal;
import com.notetaker.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("username: " + username);
        }

        return new UserPrincipal(user);
    }
}
