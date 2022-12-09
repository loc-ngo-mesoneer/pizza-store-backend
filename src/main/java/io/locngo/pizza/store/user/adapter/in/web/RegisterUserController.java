package io.locngo.pizza.store.user.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.locngo.pizza.store.common.annotation.RestApiController;
import io.locngo.pizza.store.user.application.port.in.RegisterCommand;
import io.locngo.pizza.store.user.application.port.in.RegisterResponse;
import io.locngo.pizza.store.user.application.port.in.RegisterUserCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestApiController("/user")
public class RegisterUserController {

    private final RegisterUserCase registerUserCase;

    @PostMapping(value =  "/receptionist")
    public ResponseEntity<RegisterResponse> registerReceptionist(
        @RequestBody RegisterRequest request
    ) {
        final RegisterCommand command = RegisterCommand.of(
            request.getUsername(), 
            request.getPassword(), 
            request.getEmail(), 
            request.getFirstname(), 
            request.getLastname()
        );

        RegisterResponse response = this.registerUserCase.registerReceptionist(command);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}