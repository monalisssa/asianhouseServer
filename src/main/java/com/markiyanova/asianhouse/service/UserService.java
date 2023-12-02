package com.markiyanova.asianhouse.service;

import com.markiyanova.asianhouse.entity.user.BasketEntity;
import com.markiyanova.asianhouse.entity.user.UserEntity;
import com.markiyanova.asianhouse.exception.UserAlreadyExistException;
import com.markiyanova.asianhouse.exception.UserNotFoundException;
import com.markiyanova.asianhouse.model.User;
import com.markiyanova.asianhouse.repository.BasketRepository;
import com.markiyanova.asianhouse.repository.RoleRepository;
import com.markiyanova.asianhouse.repository.UserRepository;
import com.markiyanova.asianhouse.security.auth.AuthenticationRequest;
import com.markiyanova.asianhouse.security.auth.AuthenticationUser;
import com.markiyanova.asianhouse.security.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final BasketRepository basketRepo;


    public AuthenticationUser registration(UserEntity user) throws UserAlreadyExistException {
        if (userRepo.findUserEntityByUsername(user.getUsername()).isPresent() || userRepo.findUserEntityByPassword(user.getPassword()) != null) {
            throw new UserAlreadyExistException("Пользователь с таким именем или паролем уже существует!");
        }
        user.setRole(roleRepo.findById(2L).get());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        UserEntity savedUser = userRepo.save(user);

        BasketEntity basketEntity = new BasketEntity();
        basketEntity.setUser(savedUser);
        basketRepo.save(basketEntity);

        var jwtToken = jwtService.generateRefreshToken(user);
        return AuthenticationUser.builder()
                .token(jwtToken)
                .build();

    }

    public AuthenticationUser authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        var findUser = userRepo.findUserEntityByUsername(request.getUsername())
                .orElseThrow();


        var jwtToken = jwtService.generateRefreshToken(findUser);
        return AuthenticationUser.builder()
                .token(jwtToken)
                .build();

    }

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        userRepo.findAll().forEach(userEntity ->
                users.add(User.toModel(userEntity))
        );
        return users;
    }

    public User getOne(long id) throws UserNotFoundException {

        Optional<UserEntity> user = userRepo.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("Пользователь не найден!");
        }
        return User.toModel(user.get());
    }

    public Long delete(long id) {
        userRepo.deleteById(id);
        return id;
    }

    public AuthenticationUser refreshToken(
            HttpServletRequest request
    ) {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userName;
        AuthenticationUser authenticationUser = null;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        refreshToken = authHeader.substring(7);
        userName = jwtService.extractUsername(refreshToken);
        if (userName != null) {
            var user = userRepo.findUserEntityByUsername(userName)
                    .orElseThrow();

            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateRefreshToken(user);
                authenticationUser = AuthenticationUser.builder()
                        .token(accessToken)
                        .build();
            }
        }

        return authenticationUser;

    }
}




