package io.locngo.pizza.store.user.application.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.locngo.pizza.store.common.validation.ApiValidation;
import io.locngo.pizza.store.user.application.port.in.RegisterCommand;
import io.locngo.pizza.store.user.application.port.in.RegisterResponse;
import io.locngo.pizza.store.user.application.port.in.RegisterUserCase;
import io.locngo.pizza.store.user.application.port.out.QueryPersistedUserPort;
import io.locngo.pizza.store.user.application.port.out.PersistUserPort;
import io.locngo.pizza.store.user.domain.Email;
import io.locngo.pizza.store.user.domain.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterService implements RegisterUserCase {

    private final PersistUserPort persistUserPort;

    private final QueryPersistedUserPort queryPersistedUserPort;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public RegisterResponse registerReceptionist(final RegisterCommand command) {
        ApiValidation.requireNonNull(command, "command");

        this.validateUsernameNotExisted(command.getUsername());
        this.validateEmailNotExisted(command.getEmail());

        final String encryptedPassword = this.bCryptPasswordEncoder.encode(command.getPassword());

        final User user = User.newReceptionist(
            command.getUsername(), 
            encryptedPassword, 
            Email.of(command.getEmail()), 
            command.getFirstname(), 
            command.getLastname()
        );

        final User persitedUser = this.persistUserPort.persist(user);

        return RegisterResponse.fromUser(persitedUser);
    }

    @Override
    public RegisterResponse registerChef(final RegisterCommand command) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RegisterResponse registerShipper(final RegisterCommand command) {
        // TODO Auto-generated method stub
        return null;
    }
    
    private void validateUsernameNotExisted(final String username) {
        this.queryPersistedUserPort.getPersistedUserByUsername(username)
            .ifPresent(existedUser -> {
                throw new IllegalArgumentException(
                    String.format(
                        "User with username %s already existed!", 
                        existedUser.getUsername()
                    )
                );
            });
    }

    private void validateEmailNotExisted(final String email) {
        this.queryPersistedUserPort.getPersistedUserByEmail(email)
            .ifPresent(existedUser -> {
                throw new IllegalArgumentException(
                    String.format(
                        "User with email %s already existed!", 
                        existedUser.getEmail().getValue()
                    )
                );
            });
    }
}
