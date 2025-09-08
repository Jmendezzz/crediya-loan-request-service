package co.com.crediya.model.loanapplication.factories;

import co.com.crediya.model.loanapplication.LoanApplication;
import co.com.crediya.model.loanapplication.events.LoanApplicationAutoValidationCompleted;
import co.com.crediya.model.loanapplication.events.LoanApplicationAutoValidationRequested;
import co.com.crediya.model.loanapplication.events.LoanApplicationStateChanged;
import co.com.crediya.model.loanapplicationstate.LoanApplicationState;
import co.com.crediya.model.loanapplicationstate.constants.LoanApplicationStateConstant;
import co.com.crediya.model.loanapplicationtype.LoanApplicationType;
import co.com.crediya.model.loanapplicationtype.constants.LoanApplicationTypeConstant;
import co.com.crediya.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LoanApplicationEventFactoryTest {

    private LoanApplicationEventFactory factory;
    private LoanApplication loan;
    private LoanApplicationType type;
    private LoanApplicationState state;
    private User user;

    @BeforeEach
    void setUp() {
        factory = new LoanApplicationEventFactory();

        state = LoanApplicationState.builder()
                .id(1L)
                .name(LoanApplicationStateConstant.PENDING_REVIEW.getName())
                .description(LoanApplicationStateConstant.PENDING_REVIEW.getDescription())
                .build();

        type = LoanApplicationType.builder()
                .id(1L)
                .name(LoanApplicationTypeConstant.CONSUMO.getName())
                .interestRate(LoanApplicationTypeConstant.CONSUMO.getInterestRate())
                .minAmount(LoanApplicationTypeConstant.CONSUMO.getMinAmount())
                .maxAmount(LoanApplicationTypeConstant.CONSUMO.getMaxAmount())
                .autoValidation(LoanApplicationTypeConstant.CONSUMO.getAutoValidation())
                .description(LoanApplicationTypeConstant.CONSUMO.getDescription())
                .build();

        loan = LoanApplication.builder()
                .id(100L)
                .customerIdentityNumber("12345")
                .amount(1_000_000.0)
                .termInMonths(12)
                .type(type)
                .state(state)
                .build();

        user = User.builder()
                .identityNumber("12345")
                .email("user@test.com")
                .firstName("Test")
                .lastName("User")
                .baseSalary(3_000_000L)
                .build();
    }

    @Test
    void shouldBuildAutoValidationRequestedWithActiveLoans() {
        LoanApplication activeLoan = LoanApplication.builder()
                .id(200L)
                .amount(500_000.0)
                .termInMonths(6)
                .type(type)
                .state(state)
                .build();

        LoanApplicationAutoValidationRequested event =
                factory.buildAutoValidationRequested(loan, type, user, List.of(activeLoan));

        assertThat(event.loanApplicationId()).isEqualTo(loan.getId());
        assertThat(event.customerIdentityNumber()).isEqualTo(loan.getCustomerIdentityNumber());
        assertThat(event.customerEmail()).isEqualTo(user.email());
        assertThat(event.newLoan().amount()).isEqualTo(loan.getAmount());
        assertThat(event.activeLoans()).hasSize(1);
        assertThat(event.activeLoans().get(0).loanId()).isEqualTo(activeLoan.getId());
    }

    @Test
    void shouldBuildStateChanged() {
        LoanApplicationStateChanged event = factory.buildStateChanged(loan, user);

        assertThat(event.loanApplicationId()).isEqualTo(loan.getId());
        assertThat(event.loanApplicationNewState()).isEqualTo(state.getName());
        assertThat(event.customerIdentityNumber()).isEqualTo(user.identityNumber());
        assertThat(event.customerEmail()).isEqualTo(user.email());
        assertThat(event.customerFirstName()).isEqualTo(user.firstName());
        assertThat(event.customerLastName()).isEqualTo(user.lastName());
        assertThat(event.paymentPlan()).isEmpty();
    }

    @Test
    void shouldBuildStateChangedWithPlan() {
        var plan = List.of(new LoanApplicationAutoValidationCompleted.PaymentPlanInstallment(
                1, 100.0, 10.0, 900.0
        ));

        LoanApplicationStateChanged event = factory.buildStateChangedWithPlan(loan, user, plan);

        assertThat(event.paymentPlan()).hasSize(1);
        assertThat(event.paymentPlan().get(0).month()).isEqualTo(1);
    }

    @Test
    void shouldBuildStateChangedWithNullPlan() {
        LoanApplicationStateChanged event = factory.buildStateChangedWithPlan(loan, user, null);

        assertThat(event.paymentPlan()).isEmpty();
    }
}
