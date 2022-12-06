package io.locngo.pizza.store.user.application.port.out;

import java.util.Optional;

import io.locngo.pizza.store.user.domain.User;

public interface QueryPersistedUserPort {

    Optional<User> getPersistedUserByUsername(String username);

    Optional<User> getPersistedUserByEmail(String email);
}
