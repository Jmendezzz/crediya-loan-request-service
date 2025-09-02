package co.com.crediya.api.rest.loanapplication.dto;

import java.util.Optional;

public record LoanApplicationFilterRequestDto(
        Optional<String> customerIdentityNumber,
        Optional<Double> amount,
        Optional<Integer> termInMonths,
        Optional<Long> loanApplicationStateId,
        Optional<Long> loanApplicationTypeId
) { }