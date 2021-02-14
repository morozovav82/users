package ru.morozov.users.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.morozov.messages.UserRegisteredMsg;
import ru.morozov.users.mapper.UserMapper;
import ru.morozov.users.dto.NewUserDto;
import ru.morozov.users.dto.UserDto;
import ru.morozov.users.entity.User;
import ru.morozov.users.exceptions.NotFoundException;
import ru.morozov.users.producer.UserProducer;
import ru.morozov.users.repo.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserProducer userProducer;

    public UserDto create(NewUserDto user) {
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
        userProducer.sendUserRegisteredMessage(new UserRegisteredMsg(userDto.getId()));

        return userDto;
    }

    public UserDto get(Long id) {
        Optional<User> res = userRepository.findById(id);
        if (res.isPresent()) {
            User user = res.get();
            return UserMapper.convertUserToUserDto(user);
        } else {
            throw new NotFoundException(id);
        }
    }

    public void delete(Long id) {
        Optional<User> res = userRepository.findById(id);
        if (res.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new NotFoundException(id);
        }
    }

    public void update(Long id, NewUserDto user) {
        Optional<User> res = userRepository.findById(id);
        if (res.isPresent()) {
            User userEntry = UserMapper.convertNewUserDtoToUser(user);
            userEntry.setId(id);
            userRepository.save(userEntry);
        } else {
            throw new NotFoundException(id);
        }
    }
}
