package co.com.crediya.api.rest.loanapplication.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LoanApplicationPathVariable {
    LOAN_APPLICATION_ID("id");

    private final String pathVariable;
}
