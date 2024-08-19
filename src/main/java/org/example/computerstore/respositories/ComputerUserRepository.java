package org.example.computerstore.respositories;

import org.example.computerstore.entities.ComputerUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComputerUserRepository extends JpaRepository<ComputerUser, Long> {
    ComputerUser findByUsername(String username);
}
