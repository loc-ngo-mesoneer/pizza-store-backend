package io.locngo.pizza.store.user.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import io.locngo.pizza.store.common.validation.ApiValidation;
import lombok.Getter;

@Getter
public class User {

    private final UserId id;

    private final String username;

    private final String password;

    private final Email email;

    private final String firstname;

    private final String lastname;

    private final Role role;

    private final LocalDateTime lastLoginAt;

    private final boolean blocked;

    private final boolean active;
    
    private User(
        final UserId id,
        final String username,
        final String password,
        final Email email,
        final String firstname,
        final String lastname,
        final Role role,
        final LocalDateTime lastLoginAt,
        final boolean blocked,
        final boolean active
    ) {
        ApiValidation.requireNonNull(id, "id");
        ApiValidation.requireStringNonBlank(username, "username");
        ApiValidation.requireStringNonBlank(password, "password");
        ApiValidation.requireNonNull(email, "email");
        ApiValidation.requireStringNonBlank(firstname, "firstname");
        ApiValidation.requireStringNonBlank(lastname, "lastname");
        ApiValidation.requireNonNull(role, "role");
        ApiValidation.requireNonNull(lastLoginAt, "lastLoginAt");

        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
        this.lastLoginAt = lastLoginAt;
        this.blocked = blocked;
        this.active = active;
    }

    public static User of(
        final UserId id,
        final String username,
        final String password,
        final Email email,
        final String firstname,
        final String lastname,
        final Role role,
        final LocalDateTime lastLoginAt,
        final boolean blocked,
        final boolean active
    ) {
        return new User(
            id, 
            username, 
            password, 
            email,
            firstname, 
            lastname, 
            role, 
            lastLoginAt, 
            blocked, 
            active
        );
    }

    public static User newReceptionist(
        final String username,
        final String password,
        final Email email,
        final String firstname,
        final String lastname
    ) {
        return new User(
            UserId.newInstance(), 
            username, 
            password, 
            email,
            firstname, 
            lastname, 
            Role.ROLE_RECEPTIONIST, 
            LocalDateTime.now(), 
            false, 
            true
        );
    }

    public static User newChef(
        final String username,
        final String password,
        final Email email,
        final String firstname,
        final String lastname
    ) {
        return new User(
            UserId.newInstance(), 
            username, 
            password, 
            email,
            firstname, 
            lastname, 
            Role.ROLE_CHEF, 
            LocalDateTime.now(), 
            false, 
            true
        );
    }

    public static User newShipper(
        final String username,
        final String password,
        final Email email,
        final String firstname,
        final String lastname
    ) {
        return new User(
            UserId.newInstance(), 
            username, 
            password, 
            email,
            firstname, 
            lastname, 
            Role.ROLE_SHIPPER, 
            LocalDateTime.now(), 
            false, 
            true
        );
    }

    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }



    @Getter
    public static class UserId {

        private final UUID value;

        private UserId(final UUID value) {
            this.value = value;
        }

        public static UserId newInstance() {
            return new UserId(UUID.randomUUID());
        }

        public static UserId fromString(final String value) {
            return new UserId(UUID.fromString(value));
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
            UserId other = (UserId) obj;
            if (value == null) {
                if (other.value != null)
                    return false;
            } else if (!value.equals(other.value))
                return false;
            return true;
        }  
    }
}
