package ru.morozov.users;

import ru.morozov.users.dto.NewUserDto;
import ru.morozov.users.dto.UserDto;
import ru.morozov.users.entity.User;

public class UserMapper {

    public static UserDto convertUserToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstname(user.getFirstname());
        userDto.setLastname(user.getLastname());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());

        return userDto;
    }

    public static User convertNewUserDtoToUser(NewUserDto newUserDto) {
        User user = new User();
        user.setUsername(newUserDto.getUsername());
        user.setFirstname(newUserDto.getFirstname());
        user.setLastname(newUserDto.getLastname());
        user.setEmail(newUserDto.getEmail());
        user.setPhone(newUserDto.getPhone());
        user.setPassword(newUserDto.getPassword());

        return user;
    }

    public static User convertUserDtoToUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setId(userDto.getId());

        return user;
    }


}
