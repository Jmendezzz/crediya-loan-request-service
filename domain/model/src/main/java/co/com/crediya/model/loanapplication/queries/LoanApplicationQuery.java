package co.com.crediya.model.loanapplication.queries;

import java.util.Optional;

public record LoanApplicationQuery(
        Optional<String> customerIdentityNumber,
        Optional<Double> amount,
        Optional<Integer> termInMonths,
        Optional<Long> loanApplicationStateId,
        Optional<Long> loanApplicationTypId
) {
}
