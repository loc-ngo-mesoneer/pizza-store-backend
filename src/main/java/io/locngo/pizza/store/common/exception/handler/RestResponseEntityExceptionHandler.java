package io.locngo.pizza.store.common.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.locngo.pizza.store.common.exception.EmailAlreadyExistedException;
import io.locngo.pizza.store.common.exception.UsernameAlreadyExistedException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ UsernameAlreadyExistedException.class })
    public ResponseEntity<HttpExceptionResponse> handleUsernameAlreadyExistedException(
        UsernameAlreadyExistedException exception
    ) {
        log.error(exception.getLocalizedMessage());

        final HttpExceptionResponse response = this.createHttpResponse(HttpStatus.CONFLICT, exception);

        return new ResponseEntity<HttpExceptionResponse>(response, response.getStatus());
    }

    @ExceptionHandler({ EmailAlreadyExistedException.class })
    public ResponseEntity<HttpExceptionResponse> handleEmailAlreadyExistedException(
        EmailAlreadyExistedException exception
    ) {
        log.error(exception.getLocalizedMessage());

        final HttpExceptionResponse response = this.createHttpResponse(HttpStatus.CONFLICT, exception);

        return new ResponseEntity<HttpExceptionResponse>(response, response.getStatus());
    }

    @ExceptionHandler({ UsernameNotFoundException.class })
    public ResponseEntity<HttpExceptionResponse> handleUsernameNotFoundException(
        UsernameNotFoundException exception
    ) {
        log.error(exception.getLocalizedMessage());

        final HttpExceptionResponse response = this.createHttpResponse(HttpStatus.NOT_FOUND, exception);

        return new ResponseEntity<HttpExceptionResponse>(response, response.getStatus());
    }
    
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<HttpExceptionResponse> handleException(Exception exception) {
        log.error(exception.getLocalizedMessage(), exception);

        final HttpExceptionResponse response = this.createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception);
        
        return new ResponseEntity<HttpExceptionResponse>(response, response.getStatus());
    }

    private HttpExceptionResponse createHttpResponse(
        final HttpStatus httpStatus, 
        final Exception exception
    ) {
        return HttpExceptionResponse.of(
            httpStatus.value(), 
            httpStatus, 
            httpStatus.getReasonPhrase(), 
            exception.getLocalizedMessage()
        );
    }
}
