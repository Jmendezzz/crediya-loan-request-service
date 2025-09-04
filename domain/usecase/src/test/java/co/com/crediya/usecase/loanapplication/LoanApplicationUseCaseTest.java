package co.com.crediya.usecase.loanapplication;

import co.com.crediya.model.auth.RoleAuth;
import co.com.crediya.model.auth.UserAuth;
import co.com.crediya.model.auth.gateways.AuthService;
import co.com.crediya.model.common.queries.PageQuery;
import co.com.crediya.model.common.queries.PagedQuery;
import co.com.crediya.model.common.results.Paginated;
import co.com.crediya.model.loanapplication.LoanApplication;
import co.com.crediya.model.loanapplication.commands.UpdateLoanApplicationStateCommand;
import co.com.crediya.model.loanapplication.exceptions.LoanApplicationNotFoundException;
import co.com.crediya.model.loanapplication.gateways.LoanApplicationEventPublisher;
import co.com.crediya.model.loanapplication.queries.LoanApplicationQuery;
import co.com.crediya.model.loanapplication.exceptions.CustomerNotFoundException;
import co.com.crediya.model.loanapplication.exceptions.LoanApplicationAmountOutOfRangeException;
import co.com.crediya.model.loanapplication.exceptions.UnauthorizedLoanApplicationException;
import co.com.crediya.model.loanapplication.gateways.LoanApplicationRepository;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserService;
import co.com.crediya.model.loanapplicationstate.LoanApplicationState;
import co.com.crediya.model.loanapplicationstate.constants.LoanApplicationStateConstant;
import co.com.crediya.model.loanapplicationstate.exceptions.LoanApplicationStateNotFoundException;
import co.com.crediya.model.loanapplicationstate.gateways.LoanApplicationStateRepository;
import co.com.crediya.model.loanapplicationtype.LoanApplicationType;
import co.com.crediya.model.loanapplicationtype.constants.LoanApplicationTypeConstant;
import co.com.crediya.model.loanapplicationtype.exceptions.LoanApplicationTypeNotFoundException;
import co.com.crediya.model.loanapplicationtype.gateways.LoanApplicationTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanApplicationUseCaseTest {

    @Mock
    private LoanApplicationRepository loanApplicationRepository;

    @Mock
    private LoanApplicationTypeRepository loanApplicationTypeRepository;

    @Mock
    private LoanApplicationStateRepository loanApplicationStateRepository;

    @Mock
    private LoanApplicationEventPublisher loanApplicationEventPublisher;

    @Mock
    private UserService userService;

    @Mock
    private AuthService authService;

    @InjectMocks
    private LoanApplicationUseCase useCase;

    private LoanApplication application;
    private LoanApplicationType loanType;
    private LoanApplicationState state;
    private UserAuth currentUser;
    private User user;

    @BeforeEach
    void setUp() {
        loanType = LoanApplicationType.builder()
                .id(1L)
                .name(LoanApplicationTypeConstant.CONSUMO.getName())
                .minAmount(LoanApplicationTypeConstant.CONSUMO.getMinAmount())
                .maxAmount(LoanApplicationTypeConstant.CONSUMO.getMaxAmount())
                .autoValidation(LoanApplicationTypeConstant.CONSUMO.getAutoValidation())
                .build();

        state = LoanApplicationState.builder()
                .id(1L)
                .name(LoanApplicationStateConstant.PENDING_REVIEW.getName())
                .description(LoanApplicationStateConstant.PENDING_REVIEW.getDescription())
                .build();

        application = LoanApplication.builder()
                .id(1L)
                .customerIdentityNumber("12345")
                .amount(1_000_000.0)
                .type(LoanApplicationType.builder().id(1L).build())
                .build();

        user  = User.builder()
                .identityNumber("12345")
                .email("user@test.com")
                .firstName("Test")
                .lastName("User")
                .baseSalary(2_000_000L)
                .build();

        currentUser = new UserAuth(3L, "test@example.com", "12345", new RoleAuth(2L, "SOLICITANTE"));
    }

    @Test
    void shouldCreateLoanApplicationWhenAllValidationsPass() {
        when(authService.getCurrentUser()).thenReturn(Mono.just(currentUser));
        when(userService.existsByIdentityNumber("12345")).thenReturn(Mono.just(true));
        when(loanApplicationTypeRepository.findById(1L)).thenReturn(Mono.just(loanType));
        when(loanApplicationStateRepository.findByName(LoanApplicationStateConstant.PENDING_REVIEW.getName()))
                .thenReturn(Mono.just(state));
        when(loanApplicationRepository.save(any())).thenAnswer(inv -> Mono.just(inv.getArgument(0)));

        Mono<LoanApplication> result = useCase.createLoanApplication(application);

        StepVerifier.create(result)
                .expectNextMatches(app ->
                        app.getType().getName().equals(LoanApplicationTypeConstant.CONSUMO.getName()) &&
                                app.getState().getName().equals(LoanApplicationStateConstant.PENDING_REVIEW.getName())
                )
                .verifyComplete();

        verify(authService).getCurrentUser();
        verify(userService).existsByIdentityNumber("12345");
        verify(loanApplicationTypeRepository).findById(1L);
        verify(loanApplicationStateRepository).findByName(LoanApplicationStateConstant.PENDING_REVIEW.getName());
        verify(loanApplicationRepository).save(any());
    }

    @Test
    void shouldThrowExceptionWhenCustomerNotFound() {
        when(authService.getCurrentUser()).thenReturn(Mono.just(currentUser));
        when(userService.existsByIdentityNumber("12345")).thenReturn(Mono.just(false));

        Mono<LoanApplication> result = useCase.createLoanApplication(application);

        StepVerifier.create(result)
                .expectError(CustomerNotFoundException.class)
                .verify();

        verify(authService).getCurrentUser();
        verify(userService).existsByIdentityNumber("12345");
        verifyNoInteractions(loanApplicationTypeRepository);
    }

    @Test
    void shouldThrowExceptionWhenTypeNotFound() {
        when(authService.getCurrentUser()).thenReturn(Mono.just(currentUser));
        when(userService.existsByIdentityNumber("12345")).thenReturn(Mono.just(true));
        when(loanApplicationTypeRepository.findById(1L)).thenReturn(Mono.empty());

        Mono<LoanApplication> result = useCase.createLoanApplication(application);

        StepVerifier.create(result)
                .expectError(LoanApplicationTypeNotFoundException.class)
                .verify();

        verify(authService).getCurrentUser();
        verify(loanApplicationTypeRepository).findById(1L);
        verifyNoInteractions(loanApplicationRepository);
    }

    @Test
    void shouldThrowExceptionWhenStateNotFound() {
        when(authService.getCurrentUser()).thenReturn(Mono.just(currentUser));
        when(userService.existsByIdentityNumber("12345")).thenReturn(Mono.just(true));
        when(loanApplicationTypeRepository.findById(1L)).thenReturn(Mono.just(loanType));
        when(loanApplicationStateRepository.findByName(LoanApplicationStateConstant.PENDING_REVIEW.getName()))
                .thenReturn(Mono.empty());

        Mono<LoanApplication> result = useCase.createLoanApplication(application);

        StepVerifier.create(result)
                .expectError(LoanApplicationStateNotFoundException.class)
                .verify();

        verify(authService).getCurrentUser();
        verify(loanApplicationStateRepository).findByName(LoanApplicationStateConstant.PENDING_REVIEW.getName());
        verifyNoInteractions(loanApplicationRepository);
    }

    @Test
    void shouldThrowExceptionWhenAmountIsOutOfRange() {
        application.setAmount(100.0);

        when(authService.getCurrentUser()).thenReturn(Mono.just(currentUser));
        when(userService.existsByIdentityNumber("12345")).thenReturn(Mono.just(true));
        when(loanApplicationTypeRepository.findById(1L)).thenReturn(Mono.just(loanType));

        Mono<LoanApplication> result = useCase.createLoanApplication(application);

        StepVerifier.create(result)
                .expectError(LoanApplicationAmountOutOfRangeException.class)
                .verify();

        verify(authService).getCurrentUser();
        verify(loanApplicationTypeRepository).findById(1L);
        verifyNoInteractions(loanApplicationStateRepository);
        verifyNoInteractions(loanApplicationRepository);
    }

    @Test
    void shouldThrowExceptionWhenAmountExceedsMax() {
        application.setAmount((double) (loanType.getMaxAmount() + 1));

        when(authService.getCurrentUser()).thenReturn(Mono.just(currentUser));
        when(userService.existsByIdentityNumber("12345")).thenReturn(Mono.just(true));
        when(loanApplicationTypeRepository.findById(1L)).thenReturn(Mono.just(loanType));

        Mono<LoanApplication> result = useCase.createLoanApplication(application);

        StepVerifier.create(result)
                .expectError(LoanApplicationAmountOutOfRangeException.class)
                .verify();

        verify(authService).getCurrentUser();
        verify(loanApplicationTypeRepository).findById(1L);
        verifyNoInteractions(loanApplicationStateRepository);
        verifyNoInteractions(loanApplicationRepository);
    }

    @Test
    void shouldCreateLoanApplicationWithManualReviewWhenAutoValidationIsFalse() {
        LoanApplicationType manualType = LoanApplicationType.builder()
                .id(2L)
                .name(LoanApplicationTypeConstant.LIBRE_INVERSION.getName())
                .minAmount(LoanApplicationTypeConstant.LIBRE_INVERSION.getMinAmount())
                .maxAmount(LoanApplicationTypeConstant.LIBRE_INVERSION.getMaxAmount())
                .autoValidation(false)
                .build();

        LoanApplicationState manualState = LoanApplicationState.builder()
                .id(2L)
                .name(LoanApplicationStateConstant.MANUAL_REVIEW.getName())
                .description(LoanApplicationStateConstant.MANUAL_REVIEW.getDescription())
                .build();

        application.setAmount(2_000_000.0);
        application.setType(LoanApplicationType.builder().id(2L).build());

        when(authService.getCurrentUser()).thenReturn(Mono.just(currentUser));
        when(userService.existsByIdentityNumber("12345")).thenReturn(Mono.just(true));
        when(loanApplicationTypeRepository.findById(2L)).thenReturn(Mono.just(manualType));
        when(loanApplicationStateRepository.findByName(LoanApplicationStateConstant.MANUAL_REVIEW.getName()))
                .thenReturn(Mono.just(manualState));
        when(loanApplicationRepository.save(any())).thenAnswer(inv -> Mono.just(inv.getArgument(0)));

        Mono<LoanApplication> result = useCase.createLoanApplication(application);

        StepVerifier.create(result)
                .expectNextMatches(app ->
                        app.getType().getName().equals(LoanApplicationTypeConstant.LIBRE_INVERSION.getName()) &&
                                app.getState().getName().equals(LoanApplicationStateConstant.MANUAL_REVIEW.getName())
                )
                .verifyComplete();

        verify(authService).getCurrentUser();
        verify(userService).existsByIdentityNumber("12345");
        verify(loanApplicationTypeRepository).findById(2L);
        verify(loanApplicationStateRepository).findByName(LoanApplicationStateConstant.MANUAL_REVIEW.getName());
        verify(loanApplicationRepository).save(any());
    }
    @Test
    void shouldThrowExceptionWhenIdentityNumberDoesNotMatchCurrentUser() {
        UserAuth anotherUser = new UserAuth(4L, "other@example.com", "99999", new RoleAuth(2L, "SOLICITANTE"));
        when(authService.getCurrentUser()).thenReturn(Mono.just(anotherUser));
        when(userService.existsByIdentityNumber(anyString())).thenReturn(Mono.just(true));

        Mono<LoanApplication> result = useCase.createLoanApplication(application);

        StepVerifier.create(result)
                .expectError(UnauthorizedLoanApplicationException.class)
                .verify();

        verify(authService).getCurrentUser();
        verifyNoInteractions(loanApplicationTypeRepository);
        verifyNoInteractions(loanApplicationRepository);
    }

    @Test
    void shouldReturnPaginatedLoanApplicationsWhenRepositorySucceeds() {
        LoanApplicationQuery filter = new LoanApplicationQuery(
                Optional.of("12345"),
                Optional.of(1_000_000.0),
                Optional.of(12),
                Optional.of(1L),
                Optional.of(1L)
        );
        PageQuery pageQuery = new PageQuery(0, 10);
        PagedQuery<LoanApplicationQuery> pagedQuery = new PagedQuery<>(filter, pageQuery);

        LoanApplication loanApp = LoanApplication.builder()
                .id(1L)
                .customerIdentityNumber("12345")
                .amount(1_000_000.0)
                .termInMonths(12)
                .build();

        Paginated<LoanApplication> paginated = new Paginated<>(
                List.of(loanApp),
                1L,
                0,
                10
        );

        when(loanApplicationRepository.findByCriteria(pagedQuery)).thenReturn(Mono.just(paginated));

        Mono<Paginated<LoanApplication>> result = useCase.getLoanApplications(pagedQuery);

        StepVerifier.create(result)
                .expectNextMatches(res ->
                        res.items().size() == 1 &&
                                res.items().get(0).getCustomerIdentityNumber().equals("12345") &&
                                res.totalItems() == 1L &&
                                res.page() == 0 &&
                                res.size() == 10
                )
                .verifyComplete();

        verify(loanApplicationRepository).findByCriteria(pagedQuery);
    }

    @Test
    void shouldPropagateErrorWhenRepositoryFails() {
        LoanApplicationQuery filter = new LoanApplicationQuery(
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );
        PageQuery pageQuery = new PageQuery(0, 5);
        PagedQuery<LoanApplicationQuery> pagedQuery = new PagedQuery<>(filter, pageQuery);

        RuntimeException repositoryError = new RuntimeException("Database failure");
        when(loanApplicationRepository.findByCriteria(pagedQuery)).thenReturn(Mono.error(repositoryError));

        Mono<Paginated<LoanApplication>> result = useCase.getLoanApplications(pagedQuery);

        StepVerifier.create(result)
                .expectErrorMatches(ex -> ex instanceof RuntimeException &&
                        ex.getMessage().equals("Database failure"))
                .verify();

        verify(loanApplicationRepository).findByCriteria(pagedQuery);
    }

    @Test
    void shouldUpdateLoanApplicationStateSuccessfully() {
        UpdateLoanApplicationStateCommand command =
                new UpdateLoanApplicationStateCommand(application.getId(), state.getId());

        when(loanApplicationRepository.findById(application.getId())).thenReturn(Mono.just(application));
        when(loanApplicationStateRepository.findById(state.getId())).thenReturn(Mono.just(state));

        when(userService.getUserByIdentityNumber(application.getCustomerIdentityNumber()))
                .thenReturn(Mono.just(user));

        when(loanApplicationRepository.save(application)).thenReturn(Mono.just(application));

        when(loanApplicationEventPublisher.publish(any())).thenReturn(Mono.empty());

        StepVerifier.create(useCase.updateLoanApplicationState(command))
                .expectNextMatches(updated ->
                        updated.getState().getId().equals(state.getId()) &&
                                updated.getCustomerIdentityNumber().equals("12345"))
                .verifyComplete();

        verify(loanApplicationRepository).findById(application.getId());
        verify(loanApplicationStateRepository).findById(state.getId());
        verify(userService).getUserByIdentityNumber("12345");
        verify(loanApplicationRepository).save(application);
        verify(loanApplicationEventPublisher).publish(any());
    }

    @Test
    void shouldThrowExceptionWhenLoanApplicationNotFound() {
        UpdateLoanApplicationStateCommand command =
                new UpdateLoanApplicationStateCommand(99L, state.getId());

        when(loanApplicationRepository.findById(99L)).thenReturn(Mono.empty());

        StepVerifier.create(useCase.updateLoanApplicationState(command))
                .expectError(LoanApplicationNotFoundException.class)
                .verify();

        verify(loanApplicationRepository).findById(99L);
        verifyNoInteractions(userService);
        verifyNoInteractions(loanApplicationEventPublisher);
    }

    @Test
    void shouldThrowExceptionWhenStateNotFoundInEventPusblisher() {
        UpdateLoanApplicationStateCommand command =
                new UpdateLoanApplicationStateCommand(application.getId(), 99L);

        when(loanApplicationRepository.findById(application.getId())).thenReturn(Mono.just(application));
        when(loanApplicationStateRepository.findById(99L)).thenReturn(Mono.empty());

        StepVerifier.create(useCase.updateLoanApplicationState(command))
                .expectError(LoanApplicationStateNotFoundException.class)
                .verify();

        verify(loanApplicationRepository).findById(application.getId());
        verify(loanApplicationStateRepository).findById(99L);
        verifyNoInteractions(userService);
        verifyNoInteractions(loanApplicationEventPublisher);
    }

}
