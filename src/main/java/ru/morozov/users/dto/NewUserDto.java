package ru.morozov.users.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewUserDto extends BaseUserDto {
    private String password;
}
