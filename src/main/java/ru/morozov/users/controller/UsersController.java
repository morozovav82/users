package ru.morozov.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.morozov.users.UserMapper;
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
    public void deleteUser(@PathVariable("userId") Long userId) {
        userRepository.deleteById(userId);
    }

    @PutMapping("/{userId}")
    public void updateUser(@PathVariable("userId") Long userId, @RequestBody NewUserDto user) {
        User userEntry = UserMapper.convertNewUserDtoToUser(user);
        userEntry.setId(userId);
        userRepository.save(userEntry);
    }
}
