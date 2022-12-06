package io.locngo.pizza.store.user.application.port.in;

import java.time.LocalDateTime;

import io.locngo.pizza.store.user.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class RegisterResponse {

    private final String id;

    private final String username;

    private final String email;

    private final String firstname;

    private final String lastname;

    private final String role;

    private final LocalDateTime lastLoginDate;

    private final boolean blocked;

    private final boolean active;

    public static RegisterResponse fromUser(final User user) {
        
        return RegisterResponse.of(
            user.getId().getValue().toString(), 
            user.getUsername(), 
            user.getEmail().getValue(), 
            user.getFirstname(), 
            user.getLastname(), 
            user.getRole().toString(), 
            user.getLastLoginAt(),
            user.isBlocked(), 
            user.isActive()
        );
    }    
}
