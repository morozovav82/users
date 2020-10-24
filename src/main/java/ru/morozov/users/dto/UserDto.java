package ru.morozov.users.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto extends NewUserDto {
    private Long id;
}
