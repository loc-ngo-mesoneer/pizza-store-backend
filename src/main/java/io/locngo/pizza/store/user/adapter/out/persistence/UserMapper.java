package io.locngo.pizza.store.user.adapter.out.persistence;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import io.locngo.pizza.store.common.validation.ApiValidator;
import io.locngo.pizza.store.user.domain.Email;
import io.locngo.pizza.store.user.domain.Role;
import io.locngo.pizza.store.user.domain.User;
import io.locngo.pizza.store.user.domain.User.UserId;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final UserRepository userRepository;

    public User mapToDomain(final UserJpaEntity entity) {
        ApiValidator.requireNonNull(entity, "entity");

        return User.of(
                UserId.fromString(entity.getUserId()),
                entity.getUsername(),
                entity.getPassword(),
                Email.of(entity.getEmail()),
                entity.getFirstname(),
                entity.getLastname(),
                Role.valueOf(entity.getRole()),
                entity.getLastLoginAt(),
                entity.isBlocked(),
                entity.isActive());
    }

    public UserJpaEntity mapToJpaEntity(final User user) {
        ApiValidator.requireNonNull(user, "user");

        return this.userRepository.findByUserId(user.getId().toString())
                .map(entity -> UserJpaEntity.of(
                        entity.getId(),
                        entity.getUserId(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getEmail().getValue(),
                        user.getRole().name(),
                        user.getFirstname(),
                        user.getLastname(),
                        user.getLastLoginAt(),
                        user.isBlocked(),
                        user.isActive(),
                        entity.getCreatedAt(),
                        entity.getUpdatedAt()
                    )
                )
                .orElse(UserJpaEntity.of(
                        null,
                        user.getId().getValue().toString(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getEmail().getValue(),
                        user.getRole().name(),
                        user.getFirstname(),
                        user.getLastname(),
                        user.getLastLoginAt(),
                        user.isBlocked(),
                        user.isActive(),
                        LocalDateTime.now(),
                        LocalDateTime.now()
                ));
    }
}
