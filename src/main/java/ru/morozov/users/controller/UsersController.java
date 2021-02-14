package ru.morozov.users.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.morozov.users.dto.NewUserDto;
import ru.morozov.users.dto.UserDto;
import ru.morozov.users.exceptions.NotFoundException;
import ru.morozov.users.service.UserService;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class UsersController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody NewUserDto user) {
        if (StringUtils.isEmpty(user.getUsername())) {
            throw new RuntimeException("Empty username");
        }

        return userService.create(user);
    }

    @GetMapping("/{userId:\\d+}")
    public ResponseEntity getUser(@PathVariable("userId") Long userId) {
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
        try {
            userService.update(userId, user);
            return new ResponseEntity(HttpStatus.OK);
        } catch (NotFoundException e) {
            log.warn(e.getMessage());
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
