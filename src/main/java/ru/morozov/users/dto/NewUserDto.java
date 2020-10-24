package ru.morozov.users.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import static javax.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
public class NewUserDto {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
}
