package com.markiyanova.asianhouse.controllers;

import com.markiyanova.asianhouse.entity.user.UserEntity;
import com.markiyanova.asianhouse.exception.UserAlreadyExistException;
import com.markiyanova.asianhouse.exception.UserNotFoundException;
import com.markiyanova.asianhouse.security.auth.AuthenticationRequest;
import com.markiyanova.asianhouse.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public ResponseEntity registration(@RequestBody UserEntity user)
    {
        try{
            return ResponseEntity.ok(userService.registration(user));
        }
        catch (UserAlreadyExistException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body("Произошла ошибка!");
        }

    }

    @PostMapping("/authenticate")
    public ResponseEntity authenticate(
            @RequestBody AuthenticationRequest request) {
        try
        {
            return ResponseEntity.ok(userService.authenticate(request));
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body("Не верный пароль или логин!");
        }

    }

    @GetMapping("/refresh_token")
    public ResponseEntity refresh_token(HttpServletRequest request) {
        try
        {
            return ResponseEntity.ok(userService.refreshToken(request));
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body("Токен не валидный!");
        }

    }

    @GetMapping("/all")
    public ResponseEntity getUsers()
    {
        try{

            return ResponseEntity.ok(userService.getAll());
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body("Произошла ошибка!");
        }
    }

    @GetMapping
    public ResponseEntity getOneUser(@RequestParam long id)
    {
        try{
            return ResponseEntity.ok(userService.getOne(id));
        }
        catch (UserNotFoundException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body("Произошла ошибка!");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable long id)
    {
        try{
            return ResponseEntity.ok(userService.delete(id));
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body("Произошла ошибка!");
        }
    }
}
