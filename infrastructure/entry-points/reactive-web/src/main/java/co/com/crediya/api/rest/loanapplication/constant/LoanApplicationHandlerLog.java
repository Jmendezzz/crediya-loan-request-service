package co.com.crediya.api.rest.loanapplication.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum LoanApplicationHandlerLog {
    CREATE_START("Starting createLoanApplication with request: {}"),
    CREATE_VALIDATION("Validating CreateLoanApplicationRequestDto"),
    CREATE_SUCCESS("LoanApplication created successfully with id={}"),
    CREATE_ERROR("Error while creating LoanApplication: {}");

    private final String message;

    LoanApplicationHandlerLog(String message) {
        this.message = message;
    }
}