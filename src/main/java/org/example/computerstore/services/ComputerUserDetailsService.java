package org.example.computerstore.services;

import org.example.computerstore.entities.ComputerUser;
import org.example.computerstore.respositories.ComputerUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ComputerUserDetailsService implements UserDetailsService {

    private final ComputerUserRepository repository;

    public ComputerUserDetailsService(ComputerUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        ComputerUser computerUser = repository.findByUsername(username);
        if (computerUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return User.withUsername(computerUser.getUsername())
                .password(computerUser.getPassword())
                .authorities("ROLE_USER")
                .build();
    }

}
