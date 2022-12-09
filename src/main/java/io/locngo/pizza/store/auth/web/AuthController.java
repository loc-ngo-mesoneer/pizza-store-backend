package io.locngo.pizza.store.auth.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.locngo.pizza.store.auth.jwt.JwtTokenProvider;
import io.locngo.pizza.store.common.annotation.RestApiController;
import io.locngo.pizza.store.common.validation.ApiValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestApiController("/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        ApiValidator.requireNonNull(request, "request");

        try {
            final Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getUsername(), 
                    request.getPassword()
                )
            );

            return ResponseEntity.ok().body(
                LoginResponse.of(this.jwtTokenProvider.generateJwtToken(authentication)));
        } catch(Exception exception) {
            log.error(exception.getLocalizedMessage());
            throw exception;
        }
    }
}
