package co.com.crediya.model.loanapplication.events;

import java.util.List;

public record LoanApplicationAutoValidationRequested(
        Long loanApplicationId,
        String customerIdentityNumber,
        String customerEmail,
        String customerFirstName,
        String customerLastName,
        Long customerBaseSalary,
        NewLoan newLoan,
        List<ActiveLoan> activeLoans
) {
    public record NewLoan(
            Double amount,
            Integer termInMonths,
            Double interestRate,
            String typeName
    ) {}

    public record ActiveLoan(
            Long loanId,
            Double amount,
            Integer termInMonths,
            Double interestRate,
            String startDate
    ) {}
}