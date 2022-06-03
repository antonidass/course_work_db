package com.example.consensus.repositories;

import com.example.consensus.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);


    @Transactional
    @Modifying()
    @Query(value = "SET ROLE :role", nativeQuery = true)
    void setRole(@Param("role") String role);
}
