package org.example.computerstore.services;

import jakarta.validation.Valid;
import org.example.computerstore.dto.UserRequestDTO;
import org.example.computerstore.entities.ComputerUser;
import org.example.computerstore.respositories.ComputerUserRepository;
import org.example.computerstore.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ComputerUserAccountService {
    private final ComputerUserRepository computerUserRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final JwtUtil jwtUtil;


    public ComputerUserAccountService(ComputerUserRepository computerUserRepository, JwtUtil jwtUtil) {
        this.computerUserRepository = computerUserRepository;
        this.jwtUtil = jwtUtil;
    }

    public Optional<ComputerUser> registerComputerUser(UserRequestDTO userDTO) {
        String pw = userDTO.getPassword();
        String encoded = encoder.encode(pw);
        try {
            ComputerUser cu = new ComputerUser(userDTO.getUsername(), encoded);
            ComputerUser result = computerUserRepository.save(cu);
            return Optional.of(result);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<String> loginUser(UserRequestDTO userDTO) {
        ComputerUser cu = computerUserRepository.findByUsername(userDTO.getUsername());
        if (cu == null) {
            return Optional.empty();
        }
        if (encoder.matches(userDTO.getPassword(), cu.getPassword())) {
            return Optional.of(jwtUtil.createToken(userDTO.getUsername()));
        }
        return Optional.empty();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
