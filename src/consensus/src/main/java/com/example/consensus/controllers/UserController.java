package com.example.consensus.controllers;


import com.example.consensus.entities.News;
import com.example.consensus.entities.User;
import com.example.consensus.exception.FileStorageException;
import com.example.consensus.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @GetMapping("/user/all")
    public ResponseEntity<List<?>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable(name = "id") Long id) {
        User user;
        try {
            user = userService.getUserById(id);
        }  catch (FileStorageException exc) {
            return new ResponseEntity<>("Пользователь с id = " + id + " не найден!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable(name = "id") Long id, @RequestBody User userDetails) {
        User user;
        try {
            user = userService.updateUserById(id, userDetails);
        }  catch (FileStorageException exc) {
            return new ResponseEntity<>("Пользователь с id = " + id + " не найден!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/user/")
    public ResponseEntity<?> addUser(@RequestBody User newUser) {
        User user;
        user = userService.addUser(newUser);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "id") Long id) {
        try {
            userService.deleteUser(id);
        }  catch (Exception exc) {
            return new ResponseEntity<>("Пользователь с id = " + id + " не найден!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Пользователь с id = " + id + " успешно удален!", HttpStatus.OK);
    }
}
