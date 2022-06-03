package com.example.consensus.configs;

import com.example.consensus.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Arrays;


@Component
class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent>  {
    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        Object[] array = event.getAuthentication().getAuthorities().toArray();
        String role = null;
        if (array.length == 3 || (Arrays.asList(array).contains("analyst") && Arrays.asList(array).contains("admin"))) {
            return;
        }

        if (array.length == 2 && Arrays.asList(array).contains("analyst")) {
            role = "analyst";
        } else if (array.length == 2 && Arrays.asList(array).contains("admin")) {
            role = "admin";
        } else {
            role = array[0].toString();
        }
        System.out.println(role);

        try {
            userService.setRole(role);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
