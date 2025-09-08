package co.com.crediya.model.loanapplication.factories;

import co.com.crediya.model.loanapplication.LoanApplication;
import co.com.crediya.model.loanapplication.events.LoanApplicationAutoValidationCompleted;
import co.com.crediya.model.loanapplication.events.LoanApplicationAutoValidationRequested;
import co.com.crediya.model.loanapplication.events.LoanApplicationStateChanged;
import co.com.crediya.model.loanapplicationtype.LoanApplicationType;
import co.com.crediya.model.user.User;

import java.util.List;

public class LoanApplicationEventFactory {

    public LoanApplicationAutoValidationRequested buildAutoValidationRequested(
            LoanApplication loan,
            LoanApplicationType type,
            User user,
            List<LoanApplication> activeLoans
    ) {
        List<LoanApplicationAutoValidationRequested.ActiveLoan> mappedActiveLoans = activeLoans.stream()
                .map(active -> new LoanApplicationAutoValidationRequested.ActiveLoan(
                        active.getId(),
                        active.getAmount(),
                        active.getTermInMonths(),
                        active.getType().getInterestRate(),
                        active.getState().getName()
                ))
                .toList();

        return new LoanApplicationAutoValidationRequested(
                loan.getId(),
                loan.getCustomerIdentityNumber(),
                user.email(),
                user.firstName(),
                user.lastName(),
                user.baseSalary(),
                new LoanApplicationAutoValidationRequested.NewLoan(
                        loan.getAmount(),
                        loan.getTermInMonths(),
                        type.getInterestRate(),
                        type.getName()
                ),
                mappedActiveLoans
        );
    }

    public LoanApplicationStateChanged buildStateChanged(LoanApplication loan, User user) {
        return LoanApplicationStateChanged.builder()
                .loanApplicationId(loan.getId())
                .loanApplicationNewState(loan.getState().getName())
                .customerIdentityNumber(loan.getCustomerIdentityNumber())
                .customerEmail(user.email())
                .customerFirstName(user.firstName())
                .customerLastName(user.lastName())
                .build();
    }


    public LoanApplicationStateChanged buildStateChangedWithPlan(
            LoanApplication app,
            User user,
            List<LoanApplicationAutoValidationCompleted.PaymentPlanInstallment> plan
    ) {
        return LoanApplicationStateChanged.builder()
                .loanApplicationId(app.getId())
                .loanApplicationNewState(app.getState().getName())
                .customerIdentityNumber(app.getCustomerIdentityNumber())
                .customerEmail(user.email())
                .customerFirstName(user.firstName())
                .customerLastName(user.lastName())
                .paymentPlan(plan != null ? plan : List.of())
                .build();
    }
}
