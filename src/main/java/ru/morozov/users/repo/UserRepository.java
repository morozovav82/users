package ru.morozov.users.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.morozov.users.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
