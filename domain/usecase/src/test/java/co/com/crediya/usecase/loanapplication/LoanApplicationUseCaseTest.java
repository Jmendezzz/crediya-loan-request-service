package co.com.crediya.usecase.loanapplication;

import co.com.crediya.model.auth.UserAuth;
import co.com.crediya.model.auth.gateways.AuthService;
import co.com.crediya.model.common.queries.PageQuery;
import co.com.crediya.model.common.queries.PagedQuery;
import co.com.crediya.model.common.results.Paginated;
import co.com.crediya.model.loanapplication.LoanApplication;
import co.com.crediya.model.loanapplication.commands.UpdateLoanApplicationStateCommand;
import co.com.crediya.model.loanapplication.events.LoanApplicationAutoValidationCompleted;
import co.com.crediya.model.loanapplication.events.LoanApplicationAutoValidationRequested;
import co.com.crediya.model.loanapplication.events.LoanApplicationStateChanged;
import co.com.crediya.model.loanapplication.exceptions.LoanApplicationNotFoundException;
import co.com.crediya.model.loanapplication.factories.LoanApplicationEventFactory;
import co.com.crediya.model.loanapplication.gateways.LoanApplicationEventPublisher;
import co.com.crediya.model.loanapplication.queries.LoanApplicationQuery;
import co.com.crediya.model.loanapplication.exceptions.CustomerNotFoundException;
import co.com.crediya.model.loanapplication.exceptions.LoanApplicationAmountOutOfRangeException;
import co.com.crediya.model.loanapplication.exceptions.UnauthorizedLoanApplicationException;
import co.com.crediya.model.loanapplication.gateways.LoanApplicationRepository;
import co.com.crediya.model.loanapplicationstate.constants.LoanApplicationStateConstant;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exceptions.UserNotFoundException;
import co.com.crediya.model.user.gateways.UserService;
import co.com.crediya.model.loanapplicationstate.LoanApplicationState;
import co.com.crediya.model.loanapplicationstate.exceptions.LoanApplicationStateNotFoundException;
import co.com.crediya.model.loanapplicationstate.gateways.LoanApplicationStateRepository;
import co.com.crediya.model.loanapplicationtype.LoanApplicationType;
import co.com.crediya.model.loanapplicationtype.exceptions.LoanApplicationTypeNotFoundException;
import co.com.crediya.model.loanapplicationtype.gateways.LoanApplicationTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static co.com.crediya.model.loanapplicationstate.constants.LoanApplicationStateConstant.PENDING_REVIEW;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanApplicationUseCaseTest {

    @Mock private LoanApplicationRepository loanApplicationRepository;
    @Mock private LoanApplicationTypeRepository loanApplicationTypeRepository;
    @Mock private LoanApplicationStateRepository loanApplicationStateRepository;
    @Mock private LoanApplicationEventFactory loanApplicationEventFactory;
    @Mock private LoanApplicationEventPublisher loanApplicationEventPublisher;
    @Mock private UserService userService;
    @Mock private AuthService authService;

    @InjectMocks private LoanApplicationUseCase useCase;

    private LoanApplication application;
    private LoanApplicationType type;
    private LoanApplicationState state;
    private User user;

    @BeforeEach
    void setUp() {
        type = LoanApplicationType.builder()
                .id(1L)
                .name("CONSUMO")
                .minAmount(1000L)
                .maxAmount(5000L)
                .interestRate(0.05)
                .autoValidation(true)
                .build();

        state = LoanApplicationState.builder()
                .id(1L)
                .name(PENDING_REVIEW.getName())
                .description("Pending review")
                .build();

        application = LoanApplication.builder()
                .id(10L)
                .customerIdentityNumber("12345")
                .amount(2000.0)
                .termInMonths(12)
                .type(LoanApplicationType.builder().id(1L).build())
                .build();

        user = User.builder()
                .identityNumber("12345")
                .email("user@test.com")
                .firstName("Test")
                .lastName("User")
                .baseSalary(2_000_000L)
                .build();
    }

    // ---------- createLoanApplication ----------

    @Test
    void shouldCreateLoanApplicationWithAutoValidation() {
        when(authService.getCurrentUser()).thenReturn(Mono.just(new UserAuth(1L,"u@test.com","12345",null)));
        when(userService.existsByIdentityNumber("12345")).thenReturn(Mono.just(true));
        when(loanApplicationTypeRepository.findById(1L)).thenReturn(Mono.just(type));
        when(loanApplicationStateRepository.findByName(LoanApplicationStateConstant.PENDING_REVIEW.getName()))
                .thenReturn(Mono.just(state));
        when(loanApplicationRepository.save(any())).thenAnswer(inv -> Mono.just(inv.getArgument(0)));
        when(userService.getUserByIdentityNumber("12345")).thenReturn(Mono.just(user));
        when(loanApplicationRepository.findApprovedByCustomerIdentityNumber("12345")).thenReturn(Flux.empty());
        when(loanApplicationEventFactory.buildAutoValidationRequested(any(), any(), any(), any())).thenReturn(mock(LoanApplicationAutoValidationRequested.class));
        when(loanApplicationEventPublisher.publish(any(LoanApplicationAutoValidationRequested.class))).thenReturn(Mono.empty());

        StepVerifier.create(useCase.createLoanApplication(application))
                .expectNextMatches(app -> app.getState() != null &&
                        app.getState().getName().equals(LoanApplicationStateConstant.PENDING_REVIEW.getName()))
                .verifyComplete();

        verify(loanApplicationEventPublisher).publish(any(LoanApplicationAutoValidationRequested.class));
    }

    @Test
    void shouldCreateLoanApplicationWithManualValidation() {
        type.setAutoValidation(false);

        LoanApplicationState manualState = LoanApplicationState.builder()
                .id(2L)
                .name(LoanApplicationStateConstant.MANUAL_REVIEW.getName())
                .description("Manual review")
                .build();

        when(authService.getCurrentUser())
                .thenReturn(Mono.just(new UserAuth(1L,"u@test.com","12345",null)));
        when(userService.existsByIdentityNumber("12345"))
                .thenReturn(Mono.just(true));
        when(loanApplicationTypeRepository.findById(1L))
                .thenReturn(Mono.just(type));
        when(loanApplicationStateRepository.findByName(LoanApplicationStateConstant.MANUAL_REVIEW.getName()))
                .thenReturn(Mono.just(manualState));
        when(loanApplicationRepository.save(any()))
                .thenAnswer(inv -> Mono.just(inv.getArgument(0)));

        StepVerifier.create(useCase.createLoanApplication(application))
                .expectNextMatches(app -> app.getState() != null &&
                        app.getState().getName().equals(LoanApplicationStateConstant.MANUAL_REVIEW.getName()))
                .verifyComplete();

        verifyNoInteractions(loanApplicationEventPublisher);
    }

    @Test
    void shouldThrowWhenUnauthorizedUser() {
        when(authService.getCurrentUser())
                .thenReturn(Mono.just(new UserAuth(1L, "u@test.com", "99999", null)));

        when(userService.existsByIdentityNumber(anyString()))
                .thenReturn(Mono.just(true));

        when(loanApplicationTypeRepository.findById(anyLong()))
                .thenReturn(Mono.empty());

        StepVerifier.create(useCase.createLoanApplication(application))
                .expectError(UnauthorizedLoanApplicationException.class)
                .verify();

        verify(authService).getCurrentUser();
    }
    @Test
    void shouldThrowWhenCustomerNotFound() {
        when(authService.getCurrentUser()).thenReturn(Mono.just(new UserAuth(1L,"u@test.com","12345",null)));
        when(userService.existsByIdentityNumber("12345")).thenReturn(Mono.just(false));

        StepVerifier.create(useCase.createLoanApplication(application))
                .expectError(CustomerNotFoundException.class)
                .verify();
    }

    @Test
    void shouldThrowWhenTypeNotFound() {
        when(authService.getCurrentUser()).thenReturn(Mono.just(new UserAuth(1L,"u@test.com","12345",null)));
        when(userService.existsByIdentityNumber("12345")).thenReturn(Mono.just(true));
        when(loanApplicationTypeRepository.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(useCase.createLoanApplication(application))
                .expectError(LoanApplicationTypeNotFoundException.class)
                .verify();
    }

    @Test
    void shouldThrowWhenInitialStateNotFound() {
        type.setAutoValidation(false);
        when(authService.getCurrentUser()).thenReturn(Mono.just(new UserAuth(1L,"u@test.com","12345",null)));
        when(userService.existsByIdentityNumber("12345")).thenReturn(Mono.just(true));
        when(loanApplicationTypeRepository.findById(1L)).thenReturn(Mono.just(type));
        when(loanApplicationStateRepository.findByName(LoanApplicationStateConstant.MANUAL_REVIEW.getName())).thenReturn(Mono.empty());

        StepVerifier.create(useCase.createLoanApplication(application))
                .expectError(LoanApplicationStateNotFoundException.class)
                .verify();
    }

    @Test
    void shouldThrowWhenAmountBelowMin() {
        application.setAmount(0.0);
        when(authService.getCurrentUser()).thenReturn(Mono.just(new UserAuth(1L,"u@test.com","12345",null)));
        when(userService.existsByIdentityNumber("12345")).thenReturn(Mono.just(true));
        when(loanApplicationTypeRepository.findById(1L)).thenReturn(Mono.just(type));

        StepVerifier.create(useCase.createLoanApplication(application))
                .expectError(LoanApplicationAmountOutOfRangeException.class)
                .verify();
    }

    @Test
    void shouldThrowWhenAmountAboveMax() {
        application.setAmount(10000.0);
        when(authService.getCurrentUser()).thenReturn(Mono.just(new UserAuth(1L,"u@test.com","12345",null)));
        when(userService.existsByIdentityNumber("12345")).thenReturn(Mono.just(true));
        when(loanApplicationTypeRepository.findById(1L)).thenReturn(Mono.just(type));

        StepVerifier.create(useCase.createLoanApplication(application))
                .expectError(LoanApplicationAmountOutOfRangeException.class)
                .verify();
    }

    // ---------- getLoanApplications ----------

    @Test
    void shouldReturnPaginatedResults() {
        PagedQuery<LoanApplicationQuery> query = new PagedQuery<>(mock(LoanApplicationQuery.class), new PageQuery(0,10));
        Paginated<LoanApplication> paginated = new Paginated<>(List.of(application),1L,0,10);

        when(loanApplicationRepository.findByCriteria(query)).thenReturn(Mono.just(paginated));

        StepVerifier.create(useCase.getLoanApplications(query))
                .expectNext(paginated)
                .verifyComplete();
    }

    @Test
    void shouldPropagateErrorFromRepository() {
        PagedQuery<LoanApplicationQuery> query = new PagedQuery<>(mock(LoanApplicationQuery.class), new PageQuery(0,10));
        when(loanApplicationRepository.findByCriteria(query)).thenReturn(Mono.error(new RuntimeException("DB error")));

        StepVerifier.create(useCase.getLoanApplications(query))
                .expectErrorMessage("DB error")
                .verify();
    }

    // ---------- updateLoanApplicationState ----------

    @Test
    void shouldUpdateLoanApplicationStateSuccessfully() {
        UpdateLoanApplicationStateCommand command = new UpdateLoanApplicationStateCommand(10L,1L);
        when(loanApplicationRepository.findById(10L)).thenReturn(Mono.just(application));
        when(loanApplicationStateRepository.findById(1L)).thenReturn(Mono.just(state));
        when(userService.getUserByIdentityNumber("12345")).thenReturn(Mono.just(user));
        when(loanApplicationEventFactory.buildStateChanged(any(), any())).thenReturn(mock(LoanApplicationStateChanged.class));
        when(loanApplicationRepository.save(application)).thenReturn(Mono.just(application));
        when(loanApplicationEventPublisher.publish(any(LoanApplicationStateChanged.class))).thenReturn(Mono.empty());

        StepVerifier.create(useCase.updateLoanApplicationState(command))
                .expectNext(application)
                .verifyComplete();
    }

    @Test
    void shouldThrowWhenLoanApplicationNotFound() {
        UpdateLoanApplicationStateCommand command = new UpdateLoanApplicationStateCommand(99L,1L);
        when(loanApplicationRepository.findById(99L)).thenReturn(Mono.empty());

        StepVerifier.create(useCase.updateLoanApplicationState(command))
                .expectError(LoanApplicationNotFoundException.class)
                .verify();
    }

    @Test
    void shouldThrowWhenStateNotFound() {
        UpdateLoanApplicationStateCommand command = new UpdateLoanApplicationStateCommand(10L,99L);
        when(loanApplicationRepository.findById(10L)).thenReturn(Mono.just(application));
        when(loanApplicationStateRepository.findById(99L)).thenReturn(Mono.empty());

        StepVerifier.create(useCase.updateLoanApplicationState(command))
                .expectError(LoanApplicationStateNotFoundException.class)
                .verify();
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        UpdateLoanApplicationStateCommand command = new UpdateLoanApplicationStateCommand(10L,1L);
        when(loanApplicationRepository.findById(10L)).thenReturn(Mono.just(application));
        when(loanApplicationStateRepository.findById(1L)).thenReturn(Mono.just(state));
        when(userService.getUserByIdentityNumber("12345")).thenReturn(Mono.error(new UserNotFoundException()));

        StepVerifier.create(useCase.updateLoanApplicationState(command))
                .expectError(UserNotFoundException.class)
                .verify();
    }

    // ---------- applyAutoValidationDecision ----------

    @Test
    void shouldApplyAutoValidationDecisionSuccessfully() {
        LoanApplicationAutoValidationCompleted event =
                new LoanApplicationAutoValidationCompleted(10L,"12345","APPROVED",1.0,1.0,1.0,1.0,List.of());
        LoanApplicationState newState = LoanApplicationState.builder().id(2L).name("APPROVED").build();

        when(loanApplicationRepository.findById(10L)).thenReturn(Mono.just(application));
        when(loanApplicationStateRepository.findByName("APPROVED")).thenReturn(Mono.just(newState));
        when(loanApplicationRepository.save(application)).thenReturn(Mono.just(application));

        StepVerifier.create(useCase.applyAutoValidationDecision(event))
                .expectNext(application)
                .verifyComplete();
    }

    @Test
    void shouldThrowWhenLoanApplicationNotFoundInDecision() {
        LoanApplicationAutoValidationCompleted event =
                new LoanApplicationAutoValidationCompleted(99L,"12345","APPROVED",1.0,1.0,1.0,1.0,List.of());

        when(loanApplicationRepository.findById(99L)).thenReturn(Mono.empty());

        StepVerifier.create(useCase.applyAutoValidationDecision(event))
                .expectError(LoanApplicationNotFoundException.class)
                .verify();
    }

    @Test
    void shouldThrowWhenDecisionStateNotFound() {
        LoanApplicationAutoValidationCompleted event =
                new LoanApplicationAutoValidationCompleted(10L,"12345","REJECTED",1.0,1.0,1.0,1.0,List.of());

        when(loanApplicationRepository.findById(10L)).thenReturn(Mono.just(application));
        when(loanApplicationStateRepository.findByName("REJECTED")).thenReturn(Mono.empty());

        StepVerifier.create(useCase.applyAutoValidationDecision(event))
                .expectError(LoanApplicationStateNotFoundException.class)
                .verify();
    }
}

