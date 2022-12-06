package io.locngo.pizza.store.user.application.port.out;

import io.locngo.pizza.store.user.domain.User;

public interface PersistUserPort {
    
    User persist(final User user);
}
