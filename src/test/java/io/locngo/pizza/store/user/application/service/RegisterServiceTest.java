package io.locngo.pizza.store.user.application.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.locngo.pizza.store.user.application.port.in.RegisterCommand;
import io.locngo.pizza.store.user.application.port.in.RegisterResponse;
import io.locngo.pizza.store.user.application.port.out.PersistUserPort;
import io.locngo.pizza.store.user.application.port.out.QueryPersistedUserPort;
import io.locngo.pizza.store.user.domain.Email;
import io.locngo.pizza.store.user.domain.Role;
import io.locngo.pizza.store.user.domain.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

public class RegisterServiceTest {

    private final PersistUserPort persistUserPort = Mockito.mock(
        PersistUserPort.class
    );

    private final QueryPersistedUserPort queryPersistedUserPort = Mockito.mock(
        QueryPersistedUserPort.class
    );

    private final BCryptPasswordEncoder bCryptPasswordEncoder = Mockito.mock(
        BCryptPasswordEncoder.class
    );

    private final RegisterService registerService = new RegisterService(
        this.persistUserPort,
        this.queryPersistedUserPort,
        this.bCryptPasswordEncoder
    );

    @Test
    void givenValidCommand_whenRegisterReceptionist_thenReturnReceptionistUser() {
        final RegisterCommand registerCommand = RegisterCommand.of(
            "Test username", 
            "Test password", 
            "loc.ngo.mesoneer@gmail.com", 
            "Test First Name", 
            "Test Last Name"
        );
        final String encryptedPassword = "encrypted password";
        final User user = User.newReceptionist(
            registerCommand.getUsername(), 
            encryptedPassword, 
            Email.of(registerCommand.getEmail()), 
            registerCommand.getFirstname(), 
            registerCommand.getLastname()
        );

        given(this.queryPersistedUserPort.getPersistedUserByUsername(registerCommand.getUsername()))
            .willReturn(Optional.empty());
        given(this.queryPersistedUserPort.getPersistedUserByEmail(registerCommand.getEmail()))
            .willReturn(Optional.empty());
        given(this.bCryptPasswordEncoder.encode(registerCommand.getPassword()))
            .willReturn(encryptedPassword);
        given(this.persistUserPort.persist(any(User.class)))
            .willReturn(user);

        final RegisterResponse response = this.registerService.registerReceptionist(registerCommand);

        then(this.queryPersistedUserPort)
            .should()
            .getPersistedUserByUsername(eq(registerCommand.getUsername()));
        then(this.queryPersistedUserPort)
            .should()
            .getPersistedUserByEmail(eq(registerCommand.getEmail()));
        then(this.bCryptPasswordEncoder)
            .should()
            .encode(eq(registerCommand.getPassword()));
        then(this.persistUserPort)
            .should()
            .persist(any(User.class));

        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(registerCommand.getEmail(), response.getEmail());
        assertNotNull(registerCommand.getUsername(), response.getUsername());
        assertEquals(registerCommand.getFirstname(), response.getFirstname());
        assertEquals(registerCommand.getLastname(), response.getLastname());
        assertEquals(Role.ROLE_RECEPTIONIST.name(), response.getRole());
        assertNotNull(response.getLastLoginDate());
    }

    @Test
    void giveUsernameAlreadyExisted_whenRegisterReceptionist_thenThrowException() {

    }

    @Test
    void givenEmailAlreadyExisted_whenRegisterReceptionist_thenThrowException() {

    }

    @Test
    void testRegisterChef() {

    }

    @Test
    void testRegisterShipper() {

    }
}
