package co.com.crediya.model.loanapplicationstate.exceptions;

import co.com.crediya.model.common.exceptions.DomainException;

public class LoanApplicationStateNotFoundException extends DomainException {
    public LoanApplicationStateNotFoundException() {
        super("No se encontro el estado de prestamo", "LOAN_APPLICATION_STATE_NOT_FOUND");
    }
}
