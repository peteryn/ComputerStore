package org.example.computerstore.services;

import org.example.computerstore.dto.ComputerUserDTO;
import org.example.computerstore.dto.UserRequestDTO;
import org.example.computerstore.entities.ComputerUser;
import org.example.computerstore.respositories.ComputerUserRepository;
import org.example.computerstore.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class ComputerUserAccountService {
    private final ComputerUserRepository computerUserRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final JwtUtil jwtUtil;


    public ComputerUserAccountService(ComputerUserRepository computerUserRepository, JwtUtil jwtUtil) {
        this.computerUserRepository = computerUserRepository;
        this.jwtUtil = jwtUtil;
    }

    public ComputerUser registerComputerUser(UserRequestDTO userDTO) {
        String pw = userDTO.getPassword();
        String encoded = encoder.encode(pw);
        ComputerUser cu = new ComputerUser(userDTO.getUsername(), encoded);
        ComputerUser result = computerUserRepository.save(cu);
        return result;
    }

    public String loginUser(ComputerUserDTO userDTO) {
        ComputerUser cu = computerUserRepository.findByUsername(userDTO.getUsername());
        if (cu == null) {
            return "Failure";
        }
        if (encoder.matches(userDTO.getPassword(), cu.getPassword())) {
            return jwtUtil.createToken(userDTO.getUsername());
        }
        return "Failure2";
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
