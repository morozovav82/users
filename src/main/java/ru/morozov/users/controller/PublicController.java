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
@RequestMapping("/public")
@RequiredArgsConstructor
@Slf4j
public class PublicController {

    private final UserService userService;

    @PostMapping("/reg")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody NewUserDto user) {
        if (StringUtils.isEmpty(user.getUsername())) {
            throw new RuntimeException("Empty username");
        }

        return userService.create(user);
    }

    @PostMapping("/find")
    public ResponseEntity getUser(@RequestParam("userName") String userName, @RequestParam("userPassword") String userPassword) {
        try {
            return new ResponseEntity(userService.find(userName, userPassword), HttpStatus.OK);
        } catch (NotFoundException e) {
            log.warn(e.getMessage());
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
