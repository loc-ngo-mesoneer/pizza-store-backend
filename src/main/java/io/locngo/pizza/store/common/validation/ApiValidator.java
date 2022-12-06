package io.locngo.pizza.store.common.validation;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiValidator {

    public static void requireNonNull(final Object value, final String fieldName) {
        if (Objects.isNull(value)) {
            final IllegalArgumentException exception = new IllegalArgumentException(
                String.format("{%s} must be not null", fieldName)
            );
            log.error(exception.getLocalizedMessage());
            throw exception;
        }
    }

    public static void requireStringNonBlank(final String value, final String fieldName) {
        if (StringUtils.isBlank(value)) {
            final IllegalArgumentException exception = new IllegalArgumentException(
                String.format("{%s} must be not blank", fieldName)
            );
            log.error(exception.getLocalizedMessage());
            throw exception;
        }
    }

    public static void requirePostiveNumber(final Double value, final String fieldName) {
        ApiValidator.requireNonNull(value, fieldName);

        if (0 >= value) {
            final IllegalArgumentException exception = new IllegalArgumentException(
                String.format(
                    "{%s} must be positive, but received {%s}", 
                    fieldName, 
                    value
                )
            );
            log.error(exception.getLocalizedMessage());
            throw exception;
        }
    }

    public static void requireGreaterOrEqualThanZero(final Double value, final String fieldName) {
        ApiValidator.requireNonNull(value, fieldName);

        if (0 > value) {
            final IllegalArgumentException exception = new IllegalArgumentException(
                String.format(
                    "{%s} must be greater than or equal 0, but received {%s}", 
                    fieldName, 
                    value
                )
            );
            log.error(exception.getLocalizedMessage());
            throw exception;
        }
    }

    public static void requireValidEmail(final String email, final String fieldName) {
        final boolean isValid = EmailValidator.getInstance().isValid(email);

        if (isValid) {
            return;
        }

        final IllegalArgumentException exception = new IllegalArgumentException(
            String.format(
                "{%s} is an invalid email with value {%s}",
                fieldName,
                email
            )
        );
        log.error(exception.getLocalizedMessage());
        throw exception;
    }
}
