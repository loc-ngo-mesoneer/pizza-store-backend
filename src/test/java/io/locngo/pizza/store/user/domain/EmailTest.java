package io.locngo.pizza.store.user.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class EmailTest {
    
    @Test
    void givenEmail_whenOf_thenReturnEmail() {
        final String expectedEmail = "loc.ngo.mesoneer@gmail.com";

        final Email email = Email.of(expectedEmail);

        assertNotNull(email);
        assertEquals(expectedEmail, email.getValue());
    }
}
