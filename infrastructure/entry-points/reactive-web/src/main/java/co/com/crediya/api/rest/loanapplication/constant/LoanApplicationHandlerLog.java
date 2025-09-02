package co.com.crediya.api.rest.loanapplication.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LoanApplicationHandlerLog {
    CREATE_START("Starting createLoanApplication with request: {}"),
    CREATE_VALIDATION("Validating CreateLoanApplicationRequestDto"),
    CREATE_SUCCESS("LoanApplication created successfully with id={}"),
    CREATE_ERROR("Error while creating LoanApplication: {}"),
    GET_START("Fetching loan applications with filters: {} and page: {}"),
    GET_SUCCESS("Fetched {} loan applications"),
    GET_ERROR("Error while fetching loan applications: {}");

    private final String message;
}
