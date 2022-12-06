package io.locngo.pizza.store.user.domain;

import io.locngo.pizza.store.common.validation.ApiValidator;
import lombok.Getter;

@Getter
public class Email {
    
    private final String value;

    private Email(final String value) {
        ApiValidator.requireValidEmail(value, "value");

        this.value = value;
    }

    public static Email of(final String value) {
        return new Email(value);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Email other = (Email) obj;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }
}
