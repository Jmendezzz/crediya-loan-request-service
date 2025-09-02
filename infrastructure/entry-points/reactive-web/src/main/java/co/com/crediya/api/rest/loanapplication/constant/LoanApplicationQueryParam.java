package co.com.crediya.api.rest.loanapplication.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LoanApplicationQueryParam {
    CUSTOMER_IDENTITY_NUMBER("customerIdentityNumber", "Customer identity number filter"),
    AMOUNT("amount", "Loan amount filter"),
    TERM_IN_MONTHS("termInMonths", "Loan term in months filter"),
    STATE_ID("loanApplicationStateId", "Loan application state ID filter"),
    TYPE_ID("loanApplicationTypeId", "Loan application type ID filter"),
    PAGE("page", "Page number (default 0)"),
    SIZE("size", "Page size (default 10)");

    private final String param;
    private final String description;
}