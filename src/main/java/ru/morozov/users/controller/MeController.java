package ru.morozov.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.morozov.users.dto.UserDto;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class MeController {

    @GetMapping("/me")
    public UserDto getMe() {
        return (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
