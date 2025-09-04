package co.com.crediya.model.user.exceptions;

import co.com.crediya.model.common.exceptions.DomainException;

public class UserNotFoundException extends DomainException {
    public UserNotFoundException() {
        super("No se encontro el usuario.", "USER_NOT_FOUND_EXCEPTION");
    }
}
