package com.ram.Authentication.Authorization.controller;

import com.ram.Authentication.Authorization.entity.User;
import com.ram.Authentication.Authorization.exceptions.UserAlreadyExistException;
import com.ram.Authentication.Authorization.exceptions.UserNotVerifiedException;
import com.ram.Authentication.Authorization.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserService _userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user)throws UserAlreadyExistException {
        User persistedUser = _userService.registerUser(user);

        String token = UUID.randomUUID().toString();
        String verificationUrl = "http://localhost:1010/verifyRegistrationToken?token=" + token;
        System.out.println("verification-token: "+ verificationUrl);

        _userService.saveVerificationToken(persistedUser, token);
        return persistedUser;
    }

    @PostMapping("/verifyRegistrationToken")
    public String verifyRegistrationToken(@RequestParam ("token") String token){
        return _userService.verifyToken(token);
    }

    @PostMapping("/signin") // we already have login endpoint default by spring, can't use it
    public String signInUser(@RequestParam("userName") String username, @RequestParam("userPassword") String password) throws UserNotVerifiedException {
        return _userService.signInUser(username, password);
    }

    @GetMapping("/hello")
    @PreAuthorize("hasRole('admin, user')")
    public String hello(){
        return "hello ram";
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity handleUserAlreadyExistException(UserAlreadyExistException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(UserNotVerifiedException.class)
    public ResponseEntity handleUserAlreadyExistException(UserNotVerifiedException exception){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
    }

}
