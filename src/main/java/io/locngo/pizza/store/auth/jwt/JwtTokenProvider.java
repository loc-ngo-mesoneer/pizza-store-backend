package io.locngo.pizza.store.auth.jwt;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import io.locngo.pizza.store.common.constant.SecurityConstant;
import io.locngo.pizza.store.common.validation.ApiValidator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {
    
    private final Algorithm algorithm;

    public JwtTokenProvider(
            @Value("${jwt.secret}") final String secret
    ) {
        this.algorithm = Algorithm.HMAC512(secret);
    }

    public String generateJwtToken(final Authentication authentication) {
        ApiValidator.requireNonNull(authentication, "authentication");

        return JWT.create()
                .withIssuer(SecurityConstant.ISSUER)
                .withAudience(SecurityConstant.AUDIENCE)
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusMillis(SecurityConstant.EXPIRATION_TIME_IN_MILLISECONDS))
                .withSubject(authentication.getName())
                .withClaim(SecurityConstant.AUTHORITIES, this.extractAuthoritiesFromAuthentication(authentication))
                .sign(this.algorithm);
    }

    public Authentication extractAuthenticationFromJwtToken(
            final String token,
            final HttpServletRequest request
    ) {
        ApiValidator.requireStringNonBlank(token, "token");
        ApiValidator.requireNonNull(request, "request");

        final String subject = this.extractSubjectFromJwtToken(token);
        ApiValidator.requireStringNonBlank(subject, "subject");

        final Collection<? extends GrantedAuthority> authorities = this.extractAuthoritiesFromJwtToken(token);

        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                subject,
                subject,
                authorities
        );
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        return authentication;
    }

    public Collection<? extends GrantedAuthority> extractAuthoritiesFromJwtToken(final String jwtToken) {
        ApiValidator.requireStringNonBlank(jwtToken, "jwtToken");

        final JWTVerifier jwtVerifier = this.getJwtVerifier();
        final Collection<? extends GrantedAuthority> authorities = jwtVerifier.verify(jwtToken)
                .getClaim(SecurityConstant.AUTHORITIES)
                .asList(String.class)
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return Collections.unmodifiableCollection(authorities);
    }

    public String extractSubjectFromJwtToken(final String jwtToken) {
        ApiValidator.requireStringNonBlank(jwtToken, "jwtToken");

        return this.getJwtVerifier()
                .verify(jwtToken)
                .getSubject();
    }

    private JWTVerifier getJwtVerifier() {
        try {
            return JWT.require(this.algorithm)
                    .withIssuer(SecurityConstant.ISSUER)
                    .build();
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            throw new JWTVerificationException(SecurityConstant.TOKEN_CANNOT_BE_VERIFIED_MESSAGE);
        }
    }

    private List<String> extractAuthoritiesFromAuthentication(final Authentication authentication) {
        ApiValidator.requireNonNull(authentication, "user");

        return Optional.ofNullable(authentication)
                .map(Authentication::getAuthorities)
                .map(authorities -> authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())
                )
                .orElse(Collections.emptyList());
    }
}
