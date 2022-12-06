package io.locngo.pizza.store.user.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.locngo.pizza.store.user.application.port.in.RegisterCommand;
import io.locngo.pizza.store.user.application.port.in.RegisterResponse;
import io.locngo.pizza.store.user.application.port.in.RegisterUserCase;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth/register")
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