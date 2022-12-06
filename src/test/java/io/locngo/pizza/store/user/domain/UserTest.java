package io.locngo.pizza.store.user.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import io.locngo.pizza.store.user.domain.User.UserId;

public class UserTest {

    @Test
    void givenValidInput_whenOf_thenReturnUser() {
        final UserId expectedUserId = UserId.newInstance();
        final String expectedUsername = "test_username";
        final String expectedPassword = "test_password";
        final Email expectedEmail = Email.of("loc.ngo.mesoneer@gmail.com");
        final String expectedFirstname = "test_firstname";
        final String expectedLastname = "test_lastname";
        final LocalDateTime expectedLastLoginAt = LocalDateTime.now();
        final boolean expectedBlocked = true;
        final boolean expectedActive = true;

        User user = User.of(
            expectedUserId, 
            expectedUsername, 
            expectedPassword,
            expectedEmail,
            expectedFirstname, 
            expectedLastname, 
            Role.ROLE_RECEPTIONIST, 
            expectedLastLoginAt, 
            expectedBlocked, 
            expectedActive
        );

        assertNotNull(user);
        assertEquals(expectedUserId, user.getId());
        assertEquals(expectedUsername, user.getUsername());
        assertEquals(expectedPassword, user.getPassword());
        assertEquals(expectedEmail, user.getEmail());
        assertEquals(expectedFirstname, user.getFirstname());
        assertEquals(expectedLastname, user.getLastname());
        assertEquals(Role.ROLE_RECEPTIONIST, user.getRole());
        assertEquals(expectedLastLoginAt, user.getLastLoginAt());
        assertEquals(expectedBlocked, user.isBlocked());
        assertEquals(expectedActive, user.isActive());
    }

    @Test
    void givenValidInput_whenNewReceptionist_thenReturnUser() {
        final String expectedUsername = "test_username";
        final String expectedPassword = "test_password";
        final Email expectedEmail = Email.of("loc.ngo.mesoneer@gmail.com");
        final String expectedFirstname = "test_firstname";
        final String expectedLastname = "test_lastname";

        User user = User.newReceptionist(
            expectedUsername, 
            expectedPassword,
            expectedEmail,
            expectedFirstname, 
            expectedLastname
        );

        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals(expectedUsername, user.getUsername());
        assertEquals(expectedPassword, user.getPassword());
        assertEquals(expectedEmail, user.getEmail());
        assertEquals(expectedFirstname, user.getFirstname());
        assertEquals(expectedLastname, user.getLastname());
        assertEquals(Role.ROLE_RECEPTIONIST, user.getRole());
        assertNotNull(user.getLastLoginAt());
        assertEquals(false, user.isBlocked());
        assertEquals(true, user.isActive());
    }

    @Test
    void givenValidInput_whenNewChef_thenReturnUser() {
        final String expectedUsername = "test_username";
        final String expectedPassword = "test_password";
        final Email expectedEmail = Email.of("loc.ngo.mesoneer@gmail.com");
        final String expectedFirstname = "test_firstname";
        final String expectedLastname = "test_lastname";

        User user = User.newChef(
            expectedUsername, 
            expectedPassword,
            expectedEmail,
            expectedFirstname, 
            expectedLastname
        );

        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals(expectedUsername, user.getUsername());
        assertEquals(expectedPassword, user.getPassword());
        assertEquals(expectedEmail, user.getEmail());
        assertEquals(expectedFirstname, user.getFirstname());
        assertEquals(expectedLastname, user.getLastname());
        assertEquals(Role.ROLE_CHEF, user.getRole());
        assertNotNull(user.getLastLoginAt());
        assertEquals(false, user.isBlocked());
        assertEquals(true, user.isActive());
    }

    @Test
    void givenValidInput_whenNewShipper_thenReturnUser() {
        final String expectedUsername = "test_username";
        final String expectedPassword = "test_password";
        final Email expectedEmail = Email.of("loc.ngo.mesoneer@gmail.com");
        final String expectedFirstname = "test_firstname";
        final String expectedLastname = "test_lastname";

        User user = User.newShipper(
            expectedUsername, 
            expectedPassword,
            expectedEmail,
            expectedFirstname, 
            expectedLastname
        );

        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals(expectedUsername, user.getUsername());
        assertEquals(expectedPassword, user.getPassword());
        assertEquals(expectedEmail, user.getEmail());
        assertEquals(expectedFirstname, user.getFirstname());
        assertEquals(expectedLastname, user.getLastname());
        assertEquals(Role.ROLE_SHIPPER, user.getRole());
        assertNotNull(user.getLastLoginAt());
        assertEquals(false, user.isBlocked());
        assertEquals(true, user.isActive());
    }
}
