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
    GET_ERROR("Error while fetching loan applications: {}"),

    UPDATE_STATE_START("Starting updateLoanApplicationState for id={} with request: {}"),
    UPDATE_STATE_SUCCESS("LoanApplication state updated successfully for id={}"),
    UPDATE_STATE_ERROR("Error while updating LoanApplication state for id={} - {}");

    private final String message;
}
