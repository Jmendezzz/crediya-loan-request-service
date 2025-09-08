package co.com.crediya.config;

import co.com.crediya.model.auth.gateways.AuthService;
import co.com.crediya.model.loanapplication.LoanApplication;
import co.com.crediya.model.loanapplication.factories.LoanApplicationEventFactory;
import co.com.crediya.model.loanapplication.gateways.LoanApplicationEventPublisher;
import co.com.crediya.model.loanapplication.gateways.LoanApplicationRepository;
import co.com.crediya.model.user.gateways.UserService;
import co.com.crediya.model.loanapplicationstate.gateways.LoanApplicationStateRepository;
import co.com.crediya.model.loanapplicationtype.gateways.LoanApplicationTypeRepository;
import co.com.crediya.usecase.loanapplication.LoanApplicationUseCase;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;

class UseCasesConfigTest {

    @Test
    void shouldCreateLoanApplicationUseCase() {
        LoanApplicationRepository loanApplicationRepository = mock(LoanApplicationRepository.class);
        LoanApplicationTypeRepository loanApplicationTypeRepository = mock(LoanApplicationTypeRepository.class);
        LoanApplicationStateRepository loanApplicationStateRepository = mock(LoanApplicationStateRepository.class);
        AuthService authService = mock(AuthService.class);
        UserService userService = mock(UserService.class);
        LoanApplicationEventPublisher loanApplicationEventPublisher = mock(LoanApplicationEventPublisher.class);
        LoanApplicationEventFactory loanApplicationEventFactory = mock(LoanApplicationEventFactory.class);


        UseCasesConfig config = new UseCasesConfig();
        LoanApplicationUseCase useCase = config.loanApplicationUseCase(
                loanApplicationRepository,
                loanApplicationTypeRepository,
                loanApplicationStateRepository,
                userService,
                authService,
                loanApplicationEventPublisher,
                loanApplicationEventFactory
        );

        LoanApplicationEventFactory loanApplicationEventFactoryBean = config.loanApplicationEventFactory();

        assertThat(loanApplicationEventFactoryBean).isNotNull();

        assertThat(useCase).isNotNull();
    }

}