package ru.morozov.users.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.morozov.messages.UserRegisteredMsg;
import ru.morozov.users.dto.NewUserDto;
import ru.morozov.users.dto.UserDto;
import ru.morozov.users.entity.User;
import ru.morozov.users.entity.UserRole;
import ru.morozov.users.entity.UserRoleId;
import ru.morozov.users.exceptions.NotFoundException;
import ru.morozov.users.mapper.UserMapper;
import ru.morozov.users.producer.UserProducer;
import ru.morozov.users.repo.UserRepository;
import ru.morozov.users.repo.UserRoleRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
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
            UserDto userDto = UserMapper.convertUserToUserDto(user);
            userDto.setRoles(getUserRoles(id));
            return userDto;
        } else {
            throw new NotFoundException(id);
        }
    }

    public void delete(Long id) {
        Optional<User> res = userRepository.findById(id);
        if (res.isPresent()) {
            userRepository.deleteById(id);
            userRoleRepository.deleteByIdUserId(id);
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

    public Optional<User> findOneByUsernameAndPassword(String userName, String userPassword) {
        return null;
    }

    public UserDto find(String userName, String userPassword) {
        Optional<User> res = userRepository.findOneByUsernameAndPassword(userName, userPassword);
        if (res.isPresent()) {
            User user = res.get();
            return UserMapper.convertUserToUserDto(user);
        } else {
            throw new NotFoundException(userName);
        }
    }

    public void assignRole(Long userId, String role) {
        UserRole userRole = new UserRole(new UserRoleId(userId, role));
        userRoleRepository.save(userRole);
    }

    public List<String> getUserRoles(Long userId) {
        List<UserRole> userRoles = userRoleRepository.findByIdUserId(userId);
        return userRoles.stream()
                .map(i -> i.getId().getRole())
                .collect(Collectors.toList());
    }
}
