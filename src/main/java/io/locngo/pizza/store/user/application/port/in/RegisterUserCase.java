package io.locngo.pizza.store.user.application.port.in;

public interface RegisterUserCase {

    RegisterResponse registerReceptionist(RegisterCommand command);

    RegisterResponse registerChef(RegisterCommand command);

    RegisterResponse registerShipper(RegisterCommand command);
}
