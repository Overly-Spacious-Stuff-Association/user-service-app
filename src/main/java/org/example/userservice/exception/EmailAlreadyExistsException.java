package org.example.userservice.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    private static final String EMAIL_ALREADY_EXISTS = "Email already exists %s";

    public EmailAlreadyExistsException(String email) {
        super(EMAIL_ALREADY_EXISTS.formatted(email));
    }
}
