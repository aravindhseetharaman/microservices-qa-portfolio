package com.portfolio.userservice;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class UserControllerTest {

    private UserController controller = new UserController();

    @Test
    void shouldReturnThreeUsers() {
        var response = controller.getAllUsers();
        assertThat(response.getBody()).hasSize(3);
    }

    @Test
    void shouldReturnUserById() {
        var response = controller.getUser(1L);
        assertThat(response.getBody().getName()).isEqualTo("John Doe");
        assertThat(response.getBody().getEmail()).isEqualTo("john@example.com");
    }

    @Test
    void shouldCreateUserWithGeneratedId() {
        var user = new User(null, "Alice", "alice@example.com", "+44111111111");
        var response = controller.createUser(user);
        assertThat(response.getStatusCode().value()).isEqualTo(201);
        assertThat(response.getBody().getName()).isEqualTo("Alice");
        assertThat(response.getBody().getId()).isNotNull();
    }
}
