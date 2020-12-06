package ru.morozov.users.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewUserDto {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
}
