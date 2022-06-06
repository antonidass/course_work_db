package com.example.consensus.repositories;

import com.example.consensus.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "SELECT * FROM roles JOIN users_roles ON users_roles.role_id = roles.id" +
                   " JOIN users ON users.id = users_roles.user_id WHERE users.id = ?1", nativeQuery = true)
    Collection<Role> findAllRolesByUserId(Long userId);

    @Modifying
    @Query(value = "INSERT INTO users_roles(user_id, role_id) VALUES (:user_id, :role_id)", nativeQuery = true)
    void addRoleToUser(@Param("user_id") Long user_id, @Param("role_id") Long role_id);

    @Modifying
    @Query(value = "DELETE FROM users_roles WHERE user_id = :user_id AND role_id = :role_id", nativeQuery = true)
    void deleteRoleByUserId(@Param("user_id") Long user_id, @Param("role_id") Long role_id);
}
