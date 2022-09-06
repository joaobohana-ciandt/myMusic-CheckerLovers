package com.ciandt.summit.bootcamp2022.domains.exceptions.tokens;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
