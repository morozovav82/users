package ru.morozov.users.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Long id) {
        super("Not found by id=" + id);
    }

    public NotFoundException(String username) {
        super("Not found by username=" + username + " and password=******");
    }
}
