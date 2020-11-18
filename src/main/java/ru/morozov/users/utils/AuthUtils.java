package ru.morozov.users.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import ru.morozov.users.dto.UserDto;

public class AuthUtils {

    public static Long getCurrentUserId() {
        UserDto userDto = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDto.getId();
    }
}
