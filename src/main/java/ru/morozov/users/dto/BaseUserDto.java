package ru.morozov.users.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public abstract class BaseUserDto implements Serializable {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
}
