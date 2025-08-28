package co.com.crediya.model.loanapplication.exceptions;

import co.com.crediya.model.common.exceptions.DomainException;

public class CustomerNotFoundException extends DomainException {
    public CustomerNotFoundException(String identityNumber) {
        super("No se encontro el cliente con el id: " + identityNumber,
                "CUSTOMER_NOT_FOUND");
    }
}
