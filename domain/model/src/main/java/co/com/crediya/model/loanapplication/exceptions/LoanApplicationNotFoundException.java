package co.com.crediya.model.loanapplication.exceptions;

import co.com.crediya.model.common.exceptions.DomainException;

public class LoanApplicationNotFoundException extends DomainException {
    public LoanApplicationNotFoundException() {
        super("No se encontró la solicitud de prestamo.", "LOAN_NOT_FOUND_EXCEPTION");
    }
}
