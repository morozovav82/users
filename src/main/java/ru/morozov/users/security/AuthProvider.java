package ru.morozov.users.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import ru.morozov.users.utils.UserMapper;
import ru.morozov.users.dto.UserDto;
import ru.morozov.users.entity.User;
import ru.morozov.users.repo.UserRepository;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthProvider implements AuthenticationProvider {

    private final UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        PreauthenticatedToken preauthenticatedToken = (PreauthenticatedToken) authentication;

        Optional<User> user = userRepository.findById(preauthenticatedToken.getUserId());

        if (!user.isPresent()) {
            throw new AuthenticationCredentialsNotFoundException("User not found");
        }

        UserDto userDto = UserMapper.convertUserToUserDto(user.get());
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDto, null, Collections.emptyList());

        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(PreauthenticatedToken.class);
    }
}
