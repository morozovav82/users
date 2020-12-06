package ru.morozov.users.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.morozov.users.UserMapper;
import ru.morozov.users.dto.NewAccountDto;
import ru.morozov.users.dto.NewUserDto;
import ru.morozov.users.dto.UserDto;
import ru.morozov.users.entity.User;
import ru.morozov.users.repo.UserRepository;

import java.util.Optional;

@RestController()
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UsersController {

    private final UserRepository userRepository;

    @Value("${bill.url}")
    private String billUrl;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody NewUserDto user) {
        if (StringUtils.isEmpty(user.getUsername())) {
            throw new RuntimeException("Empty username");
        }

        boolean exists = userRepository.existsByUsername(user.getUsername());
        if (exists) {
            throw new RuntimeException("User exists");
        }

        //create user
        UserDto userDto = UserMapper.convertUserToUserDto(
                userRepository.save(
                        UserMapper.convertNewUserDtoToUser(user)
                )
        );

        //create account
        RestTemplate restTemplate = new RestTemplate();
        try {
            String url = billUrl + "/account";
            log.debug("Sent request to " + url);
            NewAccountDto accountDto = new NewAccountDto();
            accountDto.setUserId(userDto.getId());
            ResponseEntity<String> result = restTemplate.postForEntity(url, accountDto, String.class);
            log.info("Account created. Result: {}", result.getBody());
        } catch (Throwable e) {
            log.error("Failed to create account for user " + user.getUsername(), e);
            userRepository.deleteById(userDto.getId());
            throw e;
        }

        return userDto;
    }

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
