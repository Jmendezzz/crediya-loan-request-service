package co.com.crediya.model.loanapplication.exceptions;

import co.com.crediya.model.common.exceptions.DomainException;

public class LoanApplicationAmountOutOfRangeException extends DomainException {

    public LoanApplicationAmountOutOfRangeException(Double requestedAmount, Long minAmount, Long maxAmount) {
        super(
                String.format("El monto %.2f está fuera del rango permitido [%,d - %,d]",
                requestedAmount, minAmount, maxAmount),
                "LOAN_APPLICATION_AMOUNT_OUT_OF_RANGE_EXCEPTION"

                );
    }
}
