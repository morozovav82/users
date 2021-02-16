package ru.morozov.users.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString(callSuper = true)
public class UserDto extends BaseUserDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
}
