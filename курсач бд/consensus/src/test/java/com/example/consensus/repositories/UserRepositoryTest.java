package com.example.consensus.repositories;

import com.example.consensus.entities.User;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private User user1, user2;
    List<User> userList;

    @BeforeEach()
    void setUp() {
        userList = new ArrayList<>();
        user1 = new User();
        user2 = new User();
        user1.setId(1L);
        user2.setId(2L);
        user1.setUsername("Test1");
        user2.setUsername("Test2");
        userList.add(user1);
        userList.add(user2);
    }

    @AfterEach
    void tearDown() {
        user1 = user2 = null;
        userList = null;
    }

    @Test
    @Order(1)
    @Rollback(value = false)
    @DisplayName("save user test")
    public void whenSaveUserItShouldReturnUser() {
        userRepository.save(user1);
        assertThat(user1).isEqualTo(userRepository.getById(user1.getId()));
    }

    @Test
    @DisplayName("find all user test")
    @Rollback(value = false)
    @Order(2)
    public void getAllUsersShouldReturnAllUsers() {
        userRepository.save(user1);
        userRepository.save(user2);
        assertThat(userRepository.findAll()).isEqualTo(userList);
    }

    @Test
    @DisplayName("find user by id test")
    @Rollback(value = false)
    @Order(3)
    public void givenIdThenShouldReturnUserOfThatId() {
        userRepository.save(user1);
        AssertionsForClassTypes.assertThat(userRepository.getById(user1.getId())).isEqualTo(user1);
    }

    @Test
    @DisplayName("delete user by id test")
    @Order(4)
    public void givenIdTODeleteThenShouldDeleteTheUser() {
        userRepository.save(user1);
        userRepository.delete(user1);
        assertThat(userRepository.findById(user1.getId()).isPresent()).isEqualTo(false);
    }

    @Test
    @DisplayName("update user by id test")
    @Rollback(value = false)
    @Order(5)
    public void updateUserByIdShouldUpdateUser() {
        userRepository.save(user2);
        User user = userRepository.getById(user2.getId());
        user.setUsername("NEW");
        assertThat(userRepository.getById(user2.getId()).getUsername()).isEqualTo("NEW");
    }
}