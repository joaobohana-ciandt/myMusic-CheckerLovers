package com.ciandt.summit.bootcamp2022.domains.exceptions.users;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
}
