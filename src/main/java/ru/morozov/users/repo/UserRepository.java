package ru.morozov.users.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.morozov.users.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    Optional<User> findOneByUsernameAndPassword(String username, String password);
}
