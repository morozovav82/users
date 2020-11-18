package ru.morozov.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.morozov.users.UserMapper;
import ru.morozov.users.dto.NewUserDto;
import ru.morozov.users.dto.UserDto;
import ru.morozov.users.entity.User;
import ru.morozov.users.repo.UserRepository;

import java.util.Optional;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicController {

    private final UserRepository userRepository;

    @PostMapping("/reg")
    public UserDto createUser(@RequestBody NewUserDto user) {
        if (StringUtils.isEmpty(user.getUsername())) {
            throw new RuntimeException("Empty username");
        }

        boolean exists = userRepository.existsByUsername(user.getUsername());
        if (exists) {
            throw new RuntimeException("User exists: " + user.getUsername());
        }

        return UserMapper.convertUserToUserDto(
                userRepository.save(
                        UserMapper.convertNewUserDtoToUser(user)
                )
        );
    }

    @PostMapping("/find")
    public ResponseEntity getUser(@PathVariable("userName") String userName, @PathVariable("userPassword") String userPassword) {
        Optional<User> res = userRepository.findOneByUsernameAndPassword(userName, userPassword);
        if (res.isPresent()) {
            return new ResponseEntity(
                    UserMapper.convertUserToUserDto(res.get()),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
