package io.locngo.pizza.store.common.provider;

import java.time.LocalDateTime;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import io.locngo.pizza.store.user.adapter.out.persistence.UserJpaEntity;
import io.locngo.pizza.store.user.adapter.out.persistence.UserMapper;
import io.locngo.pizza.store.user.adapter.out.persistence.UserRepository;
import io.locngo.pizza.store.user.application.service.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserJpaEntity entity = this.userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    final String errorMessage = String.format(
                        "Cannot found user with username [%s]",
                        username
                    );
                    final UsernameNotFoundException exception = new UsernameNotFoundException(errorMessage);

                    log.error(errorMessage, exception);
                    throw exception;
                });
        
        entity.setLastLoginAt(LocalDateTime.now());

        return UserPrincipal.newInstance(
            this.userMapper.mapToDomain(entity)
        );
    }
    
}
