package com.example.consensus.logs;

import com.example.consensus.entities.User;
import com.example.consensus.exception.FileStorageException;
import com.example.consensus.services.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;

@Aspect
@Component
public class Log {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final UserService userService;

    @Value("${app.logpath}")
    private String logPath;

    public Log(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        FileHandler fh;
        try {
            fh = new FileHandler(logPath);
            logger.addHandler(fh);
            SimpleFormatter simpleFormatter = new SimpleFormatter();
            fh.setFormatter(simpleFormatter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Pointcut("execution(* com.example.consensus.controllers.*.*(..))")
    public void anyControllerOperation() {}

    @Before("anyControllerOperation()")
    public void beforeControllerOperation(JoinPoint joinPoint) {
        String args = Arrays.stream(joinPoint.getArgs())
                .map(a -> a.toString())
                .collect(Collectors.joining(","));
        UUID uuid = UUID.randomUUID();
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String userName = ((UserDetails) principal).getUsername();
//        User user = userService.findByUsername(userName);
//
//        String msg =
//                    " log uuid = " + uuid +
//                    " user id = " + user.getId() +
//                    " before " + joinPoint + ", args = [" + args + "]";
//        logger.info(msg);
    }


    @Pointcut("execution(* com.example.consensus.services.*.*(..))")
    public void anyExceptions() {}


    @AfterThrowing(value = "anyExceptions()", throwing = "ex")
    public void afterAnyException(Exception ex) {
//        UUID uuid = UUID.randomUUID();
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        String userName = ((UserDetails) principal).getUsername();
//        User user = userService.findByUsername(userName);
//
//        String msg =
//                    " log uuid = " + uuid +
//                    " user id = " + user.getId() +
//                    " cause = " + ex.getMessage();

//        logger.log(Level.WARNING, msg);
    }
}
