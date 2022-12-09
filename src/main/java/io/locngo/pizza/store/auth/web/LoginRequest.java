package io.locngo.pizza.store.auth.web;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class LoginRequest {
    private String username;
    private String password;
}
