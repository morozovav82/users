package ru.morozov.users.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.morozov.users.entity.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySentIsNullOrderById();
}
