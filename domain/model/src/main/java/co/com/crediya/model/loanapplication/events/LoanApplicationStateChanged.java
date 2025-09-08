package co.com.crediya.model.loanapplication.events;

import lombok.Builder;

import java.util.List;

@Builder
public record LoanApplicationStateChanged(
        Long loanApplicationId,
        String loanApplicationNewState,
        String customerIdentityNumber,
        String customerEmail,
        String customerFirstName,
        String customerLastName,
        List<LoanApplicationAutoValidationCompleted.PaymentPlanInstallment> paymentPlan
) {
    public LoanApplicationStateChanged {
        if (paymentPlan == null) {
            paymentPlan = List.of();
        }
    }
}