package com.example.consensus.controllers;


import com.example.consensus.entities.User;
import com.example.consensus.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthenticationController {
    private UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<?> register(@RequestBody User newUser) {
        User user;
        user = userService.addUser(newUser);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
