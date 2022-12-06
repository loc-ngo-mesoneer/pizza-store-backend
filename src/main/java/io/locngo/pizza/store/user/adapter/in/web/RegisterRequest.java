package io.locngo.pizza.store.user.adapter.in.web;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterRequest {

    private final String username;

    private final String password;

    private final String email;

    private final String firstname;

    private final String lastname;
}
