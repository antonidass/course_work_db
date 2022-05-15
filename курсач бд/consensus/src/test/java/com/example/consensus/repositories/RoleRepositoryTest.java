package com.example.consensus.repositories;

import com.example.consensus.entities.Company;
import com.example.consensus.entities.Indicators;
import com.example.consensus.entities.Role;
import com.example.consensus.entities.User;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoleRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;

    private Role role1, role2;
    List<Role> roleList;

    @BeforeEach()
    void setUp() {
        roleList = new ArrayList<>();
        role1 = new Role();
        role2 = new Role();
        role1.setId(1L);
        role2.setId(2L);
        role1.setName("ADMIN");
        role2.setName("USER");
        roleList.add(role1);
        roleList.add(role2);
    }

    @AfterEach
    void tearDown() {
        role1 = role2 = null;
        roleList = null;
    }

    @Test
    @Order(1)
    @Rollback(value = false)
    @DisplayName("save role test")
    public void whenSaveRoleItShouldReturnRole() {
        roleRepository.save(role1);
        assertThat(role1).isEqualTo(roleRepository.getById(role1.getId()));
    }

    @Test
    @DisplayName("find all roles test")
    @Rollback(value = false)
    @Order(2)
    public void getAllRolesShouldReturnAllRoles() {
        roleRepository.save(role1);
        roleRepository.save(role2);
        assertThat(roleRepository.findAll()).isEqualTo(roleList);
    }

    @Test
    @DisplayName("find role by id test")
    @Rollback(value = false)
    @Order(3)
    public void givenIdThenShouldReturnRoleOfThatId() {
        roleRepository.save(role1);
        AssertionsForClassTypes.assertThat(roleRepository.getById(role1.getId())).isEqualTo(role1);
    }

    @Test
    @DisplayName("delete role by id test")
    @Order(4)
    public void givenIdTODeleteThenShouldDeleteTheRole() {
        roleRepository.save(role1);
        roleRepository.delete(role1);
        assertThat(roleRepository.findById(role1.getId()).isPresent()).isEqualTo(false);
    }

    @Test
    @DisplayName("update role by id test")
    @Rollback(value = false)
    @Order(5)
    public void updateRoleByIdShouldUpdateRole() {
        roleRepository.save(role2);
        Role role = roleRepository.getById(role2.getId());
        role.setName("NEW");
        assertThat(roleRepository.getById(role2.getId()).getName()).isEqualTo("NEW");
    }




}