package ru.morozov.users.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "users")
@Getter
@Setter
public class User {

    @Id
    @SequenceGenerator(name="users_gen", sequenceName="users_id_seq", allocationSize = 1)
    @GeneratedValue(strategy=SEQUENCE, generator="users_gen")
    private Long id;

    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
}
