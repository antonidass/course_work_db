package com.example.consensus.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User not founded!")
public class UsernameNotFoundedException extends UsernameNotFoundException {
    public UsernameNotFoundedException(String msg) {
        super(msg);
    }
}
