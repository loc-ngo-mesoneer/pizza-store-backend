package io.locngo.pizza.store.user.domain;

import java.util.Collection;
import java.util.List;

public enum Role {
    ROLE_RECEPTIONIST {
        @Override
        public Collection<String> getAuthorities() {
            return List.of(
                "user:read",
                "user:update",
                "order:read",
                "order:confirm"
            );
        }
    },
    ROLE_CHEF {
        @Override
        public Collection<String> getAuthorities() {
            return List.of(
                "user:read",
                "user:update",
                "order:read",
                "order:cook"
            );
        }
    },
    ROLE_SHIPPER {
        @Override
        public Collection<String> getAuthorities() {
            return List.of(
                "user:read",
                "user:update",
                "order:read",
                "order:delivery"
            );
        }
    };

    public abstract Collection<String> getAuthorities();
}
