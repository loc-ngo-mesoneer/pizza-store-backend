package io.locngo.pizza.store.user.adapter.out.persistence;

import java.util.Optional;

import org.springframework.stereotype.Component;

import io.locngo.pizza.store.common.validation.ApiValidator;
import io.locngo.pizza.store.user.application.port.out.PersistUserPort;
import io.locngo.pizza.store.user.application.port.out.QueryPersistedUserPort;
import io.locngo.pizza.store.user.domain.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements PersistUserPort, QueryPersistedUserPort {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public Optional<User> getPersistedUserByUsername(final String username) {
        return this.userRepository.findByUsername(username)
                .map(entity -> this.userMapper.mapToDomain(entity));
    }

    @Override
    public Optional<User> getPersistedUserByEmail(final String email) {
        return this.userRepository.findByEmail(email)
                .map(entity -> this.userMapper.mapToDomain(entity));
    }

    @Override
    public User persist(final User user) {
        ApiValidator.requireNonNull(user, "user");

        final UserJpaEntity entity = this.userMapper.mapToJpaEntity(user);

        return this.userMapper.mapToDomain(
            this.userRepository.save(entity)
        );
    }
    
}
