package io.locngo.pizza.store.auth.filter;

import java.io.IOException;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.locngo.pizza.store.auth.jwt.JwtTokenProvider;
import io.locngo.pizza.store.common.constant.SecurityConstant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        final String jwtToken = this.extractJwtToken(request);

        Authentication authentication = this.jwtTokenProvider.extractAuthenticationFromJwtToken(
            jwtToken, 
            request
        );

        if (Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        if (request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.toString())) {
            log.info("Handle OPTIONS method, skipped!");
            return true;
        }

        if (StringUtils.isBlank(this.extractJwtToken(request))) {
            log.info("JwtToken not existed, skipped!");
            return true;
        }

        return super.shouldNotFilter(request);
    }

    private String extractJwtToken(final HttpServletRequest request) {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith(SecurityConstant.TOKEN_PREFIX)) {
            return StringUtils.EMPTY;
        }

        return authorizationHeader.substring(SecurityConstant.TOKEN_PREFIX.length());
    }
}
