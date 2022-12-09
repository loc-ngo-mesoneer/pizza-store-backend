package io.locngo.pizza.store.auth.web;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
class LoginResponse {
    private String accessToken;
}
