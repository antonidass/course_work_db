package com.example.consensus.controllers;


import com.example.consensus.RoleRequest;
import com.example.consensus.entities.Role;
import com.example.consensus.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/user/role/{id}")
    public ResponseEntity<?> getRoleByUserId(@PathVariable(name = "id") Long id) {
        Collection<Role> roles;
        roles = roleService.getRoleByUserId(id);
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PostMapping("/user/role/")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleRequest roleRequest) {
        try {
            roleService.addRoleToUser(roleRequest);
        }  catch (Exception exc) {
            return new ResponseEntity<>("Невозможно добавить роль для пользователя с id = " + roleRequest.getUser_id(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Роль успешно добавлена!", HttpStatus.OK);
    }

    @DeleteMapping("/user/role/")
    public ResponseEntity<?> deleteRoleFromUser(@RequestBody RoleRequest roleRequest){
        try {
            roleService.deleteRoleFromUser(roleRequest);
        }  catch (Exception exc) {
            return new ResponseEntity<>("Невозможно удалить роль для пользователя с id = " + roleRequest.getUser_id(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Роль успешно удалена!", HttpStatus.OK);
    }
}
