package io.locngo.pizza.store.common.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ApiValidationTest {
    @Test
    void givenNullValue_whenRequireNonNull_thenThrowException() {
        assertThrows(
            IllegalArgumentException.class, 
            () -> {
                ApiValidator.requireNonNull(
                    null, 
                    "testParam"
                );
            }
        );
    }

    @Test
    void givenNonNullValue_whenRequireNonNull_thenDoNothing() {
        assertDoesNotThrow(() -> {
            ApiValidator.requireNonNull(
                new Object(),
                "testParam"
            );
        });
    }

    @Test
    void givenNonPositiveNumber_whenRequirePostitiveNumber_thenThrowException() {
        assertThrows(
            IllegalArgumentException.class,
            () -> ApiValidator.requirePostiveNumber(
                Double.valueOf(0), 
                "testParam"
            )
        );

        assertThrows(
            IllegalArgumentException.class,
            () -> ApiValidator.requirePostiveNumber(
                Double.valueOf(-1), 
                "testParam"
            )
        );
    }

    @Test
    void givenPositiveNumber_whenRequirePostiveNumber_thenDoNothing() {
        assertDoesNotThrow(() -> {
            ApiValidator.requireNonNull(
                Double.valueOf(1), 
                "testParam"
            );
        });
    }

    @Test
    void givenBlankString_whenRequireStringNonBlank_thenThrowException() {
        assertThrows(
            IllegalArgumentException.class, 
            () -> {
                ApiValidator.requireStringNonBlank(
                    null, 
                    "testParam"
                );
            }
        );

        assertThrows(
            IllegalArgumentException.class, 
            () -> {
                ApiValidator.requireStringNonBlank(
                    "", 
                    "testParam"
                );
            }
        );  
    }

    @Test
    void givenNonBlankString_whenRequireStringNonBlank_thenDoNothing() {
        assertDoesNotThrow(() -> {
            ApiValidator.requireStringNonBlank(
                "test",
                "testParam"
            );
        });
    }

    @Test
    void givenNumberNotGreaterThanOrEqualZero_whenRequireGreateThanOrEqualZero_thenDoNothing() {
        assertThrows(
            IllegalArgumentException.class, 
            () -> {
                ApiValidator.requireGreaterOrEqualThanZero(-0.0001, "testParam");
            }
        );

        assertThrows(
            IllegalArgumentException.class, 
            () -> {
                ApiValidator.requireGreaterOrEqualThanZero(-1000.0, "testParam");
            }
        );
    }

    @Test
    void givenNumberGreaterThanOrEqualZero_whenRequireGreateThanOrEqualZero_thenDoNothing() {
        assertDoesNotThrow(
            () -> {
                ApiValidator.requireGreaterOrEqualThanZero(0.0, "testParam");
            }
        );
        assertDoesNotThrow(
            () -> {
                ApiValidator.requireGreaterOrEqualThanZero(1000.0, "testParam");
            }
        );
    }

    @Test
    void givenValidEmail_whenRequireValidEmail_thenDoNothing() {
        final String validEmail = "loc.ngo@gmail.com";
        
        assertDoesNotThrow(() -> {
            ApiValidator.requireValidEmail(validEmail, "testParam");
        });
    }

    @Test
    void givenInvalidEmail_whenRequireValidEmail_thenThrowException() {
        final String invalidEmail = "not a valid email";
        
        assertThrows(
            IllegalArgumentException.class, 
            () -> ApiValidator.requireValidEmail(invalidEmail, "testParam")
        );
    }
}
