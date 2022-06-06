package com.example.consensus.services;

import com.example.consensus.entities.Role;
import com.example.consensus.entities.User;
import com.example.consensus.exception.FileStorageException;
import com.example.consensus.exception.UsernameNotFoundedException;
import com.example.consensus.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @PersistenceContext
    EntityManager entityManager;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundedException("User " + username + " not found!");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    @Transactional
    public void setRole(String role) throws SQLException {
        Session session = (Session) entityManager.getDelegate();
        session.doWork(connectionToUse -> {
            Statement statement1 = connectionToUse.createStatement();
            statement1.execute("set role \"" + role + "\"");
            statement1.close();
        });
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new FileStorageException("User with id = " + id + " not exists!"));
    }

    public User updateUserById(Long id, User userDetails) {
        User userNew = userRepository.findById(id).orElseThrow(() -> new FileStorageException("User with id = " + id + " not exists!"));
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode(userDetails.getPassword());
        userDetails.setPassword(encodedPassword);
        userNew.setFields(userDetails);
        return userRepository.save(userNew);
    }

    public User addUser(User newUser) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode(newUser.getPassword());
        newUser.setPassword(encodedPassword);
        return userRepository.save(newUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
