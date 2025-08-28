package co.com.crediya.model.loanapplicationtype.exceptions;

import co.com.crediya.model.common.exceptions.DomainException;

public class LoanApplicationTypeNotFoundException extends DomainException {
    public LoanApplicationTypeNotFoundException() {
        super("No se encontro el tipo de prestamo", "LOAN_APPLICATION_TYPE_NOT_FOUND");
    }
}
