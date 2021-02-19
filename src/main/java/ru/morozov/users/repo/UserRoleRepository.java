package ru.morozov.users.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.morozov.users.entity.UserRole;
import ru.morozov.users.entity.UserRoleId;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
    List<UserRole> findByIdUserId(Long userId);
    void deleteByIdUserId(Long userId);
}
