package org.example.computerstore.services;

import org.example.computerstore.dto.UserCreateDTO;
import org.example.computerstore.dto.UserDetailsDTO;
import org.example.computerstore.dto.UserLoginDTO;
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

    public Optional<ComputerUser> registerComputerUser(UserCreateDTO userDTO) {
        String pw = userDTO.getPassword();
        String encoded = encoder.encode(pw);
        try {
            ComputerUser cu = new ComputerUser(userDTO.getUsername(), encoded, userDTO.getFirstName(), userDTO.getLastName());
            ComputerUser result = computerUserRepository.save(cu);
            return Optional.of(result);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<String> loginUser(UserLoginDTO userDTO) {
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

    public void deleteUser(String username) {
        ComputerUser cu = computerUserRepository.findByUsername(username);
        computerUserRepository.delete(cu);
    }

    public Optional<ComputerUser> getUserInfo(String username) {
        ComputerUser cu = computerUserRepository.findByUsername(username);
        if (cu == null) {
            return Optional.empty();
        } else {
            return Optional.of(cu);
        }
    }

    public void updateUserInfo(String username, String firstName, String lastName) {
        ComputerUser cu = computerUserRepository.findByUsername(username);
        cu.setFirstName(firstName);
        cu.setLastName(lastName);
        computerUserRepository.save(cu);
    }

    public void updatePassword(String username, String oldPassword, String newPassword) {
        ComputerUser cu = computerUserRepository.findByUsername(username);
        if (encoder.matches(oldPassword, cu.getPassword())) {
            System.out.println("Old passwords match");
            cu.setPassword(encoder.encode(newPassword));
            computerUserRepository.save(cu);
        }
    }
}
