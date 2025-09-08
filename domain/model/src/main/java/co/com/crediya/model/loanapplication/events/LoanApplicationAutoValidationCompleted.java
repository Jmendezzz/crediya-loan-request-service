package co.com.crediya.model.loanapplication.events;

import java.util.List;

public record LoanApplicationAutoValidationCompleted(
        Long loanApplicationId,
        String customerIdentityNumber,
        String decision,
        Double maxCapacity,
        Double currentDebt,
        Double availableCapacity,
        Double newLoanInstallment,
        List<PaymentPlanInstallment> paymentPlan
) {
    public record PaymentPlanInstallment(
            Integer month,
            Double principal,
            Double interest,
            Double balance
    ) {}
}