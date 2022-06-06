package com.example.consensus.entities;

import com.example.consensus.CustomGenerator;
import com.example.consensus.repositories.UserRepository;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Collection;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(generator = "user_id")
    @GenericGenerator(
            name = "user_id",
            strategy = "com.example.consensus.generators.UserIdGenerator"
    )
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @ManyToMany
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
                                     inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    public void setFields(User userDetails) {
        setUsername(userDetails.getUsername());
        setPassword(userDetails.getPassword());
        setEmail(userDetails.getEmail());
        setName(userDetails.getName());
        setSurname(userDetails.getSurname());
    }
}
