package ru.morozov.users.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import ru.morozov.users.dto.UserDto;
import ru.morozov.users.entity.User;
import ru.morozov.users.mapper.UserMapper;
import ru.morozov.users.repo.RedisRepository;
import ru.morozov.users.repo.UserRepository;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final RedisRepository redisRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        PreauthenticatedToken preauthenticatedToken = (PreauthenticatedToken) authentication;
        Long userId = preauthenticatedToken.getUserId();

        //check user in Cache
        String cacheKey = "UserId_" + userId;
        UserDto cacheUser = (UserDto) redisRepository.find(cacheKey);

        if (cacheUser != null) {
            log.info("User found in cache. UserId=" + userId);
        } else {
            //get user from DB
            log.info("Get user from DB. UserId=" + userId);
            Optional<User> user = userRepository.findById(userId);

            if (user.isPresent()) {
                cacheUser = UserMapper.convertUserToUserDto(user.get());

                //save user in Cache
                redisRepository.add(cacheKey, cacheUser);
            }
        }

        if (cacheUser == null) {
            throw new AuthenticationCredentialsNotFoundException("User not found by ID=" + userId);
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(cacheUser, null, Collections.emptyList());

        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(PreauthenticatedToken.class);
    }
}
