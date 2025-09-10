package co.com.crediya.model.loanapplication.events;

import java.time.Instant;

public record LoanApplicationApproved(
        Long loanApplicationId,
        Double loanApplicationAmount,
        String customerIdentityNumber,
        Instant approvedAt
){
}
