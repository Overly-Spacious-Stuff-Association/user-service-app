package org.example.userservice.exception;

public class RoleNotFoundException extends RuntimeException {
    private final static String ROLE_NOT_FOUND = "Такой роли не существует.";

    public RoleNotFoundException() {
        super(ROLE_NOT_FOUND);
    }
}
