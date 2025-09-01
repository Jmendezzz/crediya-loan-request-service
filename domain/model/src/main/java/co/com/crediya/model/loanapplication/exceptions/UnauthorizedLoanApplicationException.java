package co.com.crediya.model.loanapplication.exceptions;

import co.com.crediya.model.common.exceptions.DomainException;

public class UnauthorizedLoanApplicationException extends DomainException {
    public UnauthorizedLoanApplicationException() {
        super(
                "No estas autorizado para realizar esta solicitud."
                , "UNAUTHORIZED_LOAN_APPLICATION");
    }
}
