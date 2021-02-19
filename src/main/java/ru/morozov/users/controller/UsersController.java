package ru.morozov.users.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.morozov.users.dto.NewUserDto;
import ru.morozov.users.dto.UserDto;
import ru.morozov.users.exceptions.NotFoundException;
import ru.morozov.users.service.UserService;
import ru.morozov.users.utils.AuthUtils;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UsersController {

    private final UserService userService;

    @GetMapping("/me")
    public UserDto getMe() {
        return (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping("/{userId:\\d+}")
    public ResponseEntity getUser(@PathVariable("userId") Long userId) {
        if (!AuthUtils.getCurrentUserId().equals(userId)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        try {
            return new ResponseEntity(
                    userService.get(userId),
                    HttpStatus.OK
            );
        } catch (NotFoundException e) {
            log.warn(e.getMessage());
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{userId:\\d+}")
    public ResponseEntity deleteUser(@PathVariable("userId") Long userId) {
        if (!AuthUtils.getCurrentUserId().equals(userId)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        try {
            userService.delete(userId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (NotFoundException e) {
            log.warn(e.getMessage());
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{userId:\\d+}")
    public ResponseEntity updateUser(@PathVariable("userId") Long userId, @RequestBody NewUserDto user) {
        if (!AuthUtils.getCurrentUserId().equals(userId)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        try {
            userService.update(userId, user);
            return new ResponseEntity(HttpStatus.OK);
        } catch (NotFoundException e) {
            log.warn(e.getMessage());
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{userId:\\d+}/assignRole/{role}")
    public ResponseEntity assignRole(@PathVariable("userId") Long userId, @PathVariable("role") String role) {
        try {
            userService.assignRole(userId, role);
            return new ResponseEntity(HttpStatus.OK);
        } catch (NotFoundException e) {
            log.warn(e.getMessage());
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
