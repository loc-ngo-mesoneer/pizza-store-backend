package io.locngo.pizza.store.auth.core;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.locngo.pizza.store.common.validation.ApiValidator;
import io.locngo.pizza.store.user.domain.User;
import lombok.Getter;

@Getter
public class UserPrincipal implements UserDetails {

    private final User user;

    private UserPrincipal(User user) {
        ApiValidator.requireNonNull(user, "user");
        
        this.user = user;
    }

    public static UserPrincipal newInstance(User user) {
        return new UserPrincipal(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.user.getRole()
                .getAuthorities()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.user.isBlocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.isActive();
    }
    
}
