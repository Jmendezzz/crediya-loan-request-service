package co.com.crediya.model.loanapplication.events;

import lombok.Builder;

@Builder
public record LoanApplicationStateChanged(
        Long loanApplicationId,
        String loanApplicationNewState,
        String customerIdentityNumber,
        String customerEmail,
        String customerFirstName,
        String customerLastName
) { }