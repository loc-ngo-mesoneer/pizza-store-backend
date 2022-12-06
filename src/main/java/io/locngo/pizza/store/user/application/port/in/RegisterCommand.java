package io.locngo.pizza.store.user.application.port.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class RegisterCommand {

    private final String username;
    
    private final String password;

    private final String email;
    
    private final String firstname;
    
    private final String lastname;
}
