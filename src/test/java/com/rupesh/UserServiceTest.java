package com.rupesh;

import com.rupesh.mdel.UserRequest;
import com.rupesh.mdel.UserResponse;
import com.rupesh.service.UserService;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@MicronautTest
class UserServiceTest {

    private UserService userService;
    private UserRequest userRequest;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        userRequest = new UserRequest("michel", "franande", "mchell", "michel@outlook.com", "test@outlook");
    }

    //test user insert
    @Test
    @Timeout(5)
    @Order(1)
    void testUserInsert() {
        assertNotNull(userService);
        UserResponse save = userService.save(userRequest);

        assertNotNull(save);
        assertEquals(userRequest.getFirstName(), save.getFirstName());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "John,Doe,johndoe@example.com,johndoe123,pass123",
            "Alice,Smith,alicesmith@example.com,alice123,secret123",
            "Bob,Johnson,bobjohnson@example.com,bob123,p@ssw0rd"
    })
    void testSameMultipleUsers(String userData) {
        String[] userInfo = userData.split(",");
        String firstName = userInfo[0];
        String lastName = userInfo[1];
        String email = userInfo[2];
        String username = userInfo[3];
        String password = userInfo[4];

        // Create and save the user
        UserResponse savedUser = userService.save(new UserRequest(firstName, lastName, email, username, password));

        // Perform assertions to validate the saved user
        assertNotNull(savedUser);
        assertEquals(firstName, savedUser.getFirstName());
        assertEquals(lastName, savedUser.getLastName());
        assertEquals(email, savedUser.getEmail());
        assertEquals(username, savedUser.getUsername());
        assertEquals(password, savedUser.getPassword());
    }

    //test user fetch
    @Test
    @Timeout(5)
    @Order(2)
    void testUserFetch() {
        List<UserResponse> users = userService.findUsers();

        Assertions.assertEquals(users.size(),10);
        Assertions.assertNotNull(users.get(0).getFirstName(), "David");
    }

    @AfterEach
    void shutdown() {
        System.exit(0);
    }

}
