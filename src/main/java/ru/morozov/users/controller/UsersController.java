package ru.morozov.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.morozov.users.utils.AuthUtils;
import ru.morozov.users.utils.UserMapper;
import ru.morozov.users.dto.NewUserDto;
import ru.morozov.users.entity.User;
import ru.morozov.users.repo.UserRepository;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsersController {

    private final UserRepository userRepository;

    @GetMapping("/{userId}")
    public ResponseEntity getUser(@PathVariable("userId") Long userId) {
        if (!AuthUtils.getCurrentUserId().equals(userId)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        Optional<User> res = userRepository.findById(userId);
        if (res.isPresent()) {
            return new ResponseEntity(
                    UserMapper.convertUserToUserDto(res.get()),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable("userId") Long userId) {
        if (!AuthUtils.getCurrentUserId().equals(userId)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        userRepository.deleteById(userId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity updateUser(@PathVariable("userId") Long userId, @RequestBody NewUserDto user) {
        if (!AuthUtils.getCurrentUserId().equals(userId)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        User userEntry = UserMapper.convertNewUserDtoToUser(user);
        userEntry.setId(userId);
        userRepository.save(userEntry);
        return new ResponseEntity(HttpStatus.OK);
    }
}
