package com.example.consensus.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Collection;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Email
    @Column(name = "email")
    private String email;

    @ManyToMany
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
                                     inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;
}
