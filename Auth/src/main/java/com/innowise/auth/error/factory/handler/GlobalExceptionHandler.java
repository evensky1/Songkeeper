package com.innowise.auth.error.factory.handler;

import com.innowise.auth.error.factory.exception.UserAlreadyExistsException;
import com.innowise.auth.error.factory.model.ErrorResponse;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ErrorResponse catchUserAlreadyExistsException(UserAlreadyExistsException e) {

        log.error(e.getMessage(), e);
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), Instant.now());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ErrorResponse catchUsernameNotFoundException(UsernameNotFoundException e) {

        log.error(e.getMessage(), e);
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(), Instant.now());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(JwtException.class)
    public ErrorResponse catchJwtException(JwtException e) {

        log.error(e.getMessage(), e);
        return new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), Instant.now());
    }
}

