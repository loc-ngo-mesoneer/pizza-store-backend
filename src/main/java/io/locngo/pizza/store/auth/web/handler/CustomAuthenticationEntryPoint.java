package io.locngo.pizza.store.auth.web.handler;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.locngo.pizza.store.common.constant.SecurityConstant;
import io.locngo.pizza.store.common.exception.handler.HttpExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(
        HttpServletRequest request, 
        HttpServletResponse response, 
        AuthenticationException authenticationException
    ) throws IOException {
        log.error(authenticationException.getLocalizedMessage());
        
        final HttpExceptionResponse httpResponse = HttpExceptionResponse.of(
            HttpStatus.UNAUTHORIZED.value(), 
            HttpStatus.UNAUTHORIZED, 
            HttpStatus.UNAUTHORIZED.getReasonPhrase().toUpperCase(), 
            SecurityConstant.UNAUTHORIZED_MESSAGE
        );
        
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(
            this.objectMapper.writeValueAsString(httpResponse)
        );
    }
}
