package com.modernfarmer.farmusspring.domain.auth.exception;

public class AuthRefreshTokenValidateException extends RuntimeException{

    public AuthRefreshTokenValidateException(String message) {
        super(message);
    }
}
