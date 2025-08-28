package co.com.crediya.usecase.loanapplication;

import co.com.crediya.model.loanapplication.LoanApplication;
import co.com.crediya.model.loanapplication.exceptions.CustomerNotFoundException;
import co.com.crediya.model.loanapplication.exceptions.LoanApplicationAmountOutOfRangeException;
import co.com.crediya.model.loanapplication.gateways.LoanApplicationRepository;
import co.com.crediya.model.loanapplication.gateways.UserService;
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
    private UserService userService;

    @InjectMocks
    private LoanApplicationUseCase useCase;

    private LoanApplication application;
    private LoanApplicationType loanType;
    private LoanApplicationState state;

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
    }

    @Test
    void shouldCreateLoanApplicationWhenAllValidationsPass() {
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

        verify(userService).existsByIdentityNumber("12345");
        verify(loanApplicationTypeRepository).findById(1L);
        verify(loanApplicationStateRepository).findByName(LoanApplicationStateConstant.PENDING_REVIEW.getName());
        verify(loanApplicationRepository).save(any());
    }

    @Test
    void shouldThrowExceptionWhenCustomerNotFound() {
        when(userService.existsByIdentityNumber("12345")).thenReturn(Mono.just(false));

        Mono<LoanApplication> result = useCase.createLoanApplication(application);

        StepVerifier.create(result)
                .expectError(CustomerNotFoundException.class)
                .verify();

        verify(userService).existsByIdentityNumber("12345");
        verifyNoInteractions(loanApplicationTypeRepository);
    }

    @Test
    void shouldThrowExceptionWhenTypeNotFound() {
        when(userService.existsByIdentityNumber("12345")).thenReturn(Mono.just(true));
        when(loanApplicationTypeRepository.findById(1L)).thenReturn(Mono.empty());

        Mono<LoanApplication> result = useCase.createLoanApplication(application);

        StepVerifier.create(result)
                .expectError(LoanApplicationTypeNotFoundException.class)
                .verify();

        verify(loanApplicationTypeRepository).findById(1L);
        verifyNoInteractions(loanApplicationRepository);
    }

    @Test
    void shouldThrowExceptionWhenStateNotFound() {
        when(userService.existsByIdentityNumber("12345")).thenReturn(Mono.just(true));
        when(loanApplicationTypeRepository.findById(1L)).thenReturn(Mono.just(loanType));
        when(loanApplicationStateRepository.findByName(LoanApplicationStateConstant.PENDING_REVIEW.getName()))
                .thenReturn(Mono.empty());

        Mono<LoanApplication> result = useCase.createLoanApplication(application);

        StepVerifier.create(result)
                .expectError(LoanApplicationStateNotFoundException.class)
                .verify();

        verify(loanApplicationStateRepository).findByName(LoanApplicationStateConstant.PENDING_REVIEW.getName());
        verifyNoInteractions(loanApplicationRepository);
    }

    @Test
    void shouldThrowExceptionWhenAmountIsOutOfRange() {
        application.setAmount(100.0);

        when(userService.existsByIdentityNumber("12345")).thenReturn(Mono.just(true));
        when(loanApplicationTypeRepository.findById(1L)).thenReturn(Mono.just(loanType));

        Mono<LoanApplication> result = useCase.createLoanApplication(application);

        StepVerifier.create(result)
                .expectError(LoanApplicationAmountOutOfRangeException.class)
                .verify();

        verify(loanApplicationTypeRepository).findById(1L);
        verifyNoInteractions(loanApplicationStateRepository);
        verifyNoInteractions(loanApplicationRepository);
    }

    @Test
    void shouldThrowExceptionWhenAmountExceedsMax() {
        application.setAmount((double) (loanType.getMaxAmount() + 1));
        when(userService.existsByIdentityNumber("12345")).thenReturn(Mono.just(true));
        when(loanApplicationTypeRepository.findById(1L)).thenReturn(Mono.just(loanType));

        Mono<LoanApplication> result = useCase.createLoanApplication(application);

        StepVerifier.create(result)
                .expectError(LoanApplicationAmountOutOfRangeException.class)
                .verify();

        verify(loanApplicationTypeRepository).findById(1L);
        verifyNoInteractions(loanApplicationStateRepository);
        verifyNoInteractions(loanApplicationRepository);
    }

    @Test
    void shouldCreateLoanApplicationWithManualReviewWhenAutoValidationIsFalse() {
        LoanApplicationType manualType = LoanApplicationType.builder()
                .id(2L)
                .name(LoanApplicationTypeConstant.LIBRE_INVERSION.getName()) // este tiene autoValidation=false
                .minAmount(LoanApplicationTypeConstant.LIBRE_INVERSION.getMinAmount())
                .maxAmount(LoanApplicationTypeConstant.LIBRE_INVERSION.getMaxAmount())
                .autoValidation(false) // 👈 clave
                .build();

        LoanApplicationState manualState = LoanApplicationState.builder()
                .id(2L)
                .name(LoanApplicationStateConstant.MANUAL_REVIEW.getName())
                .description(LoanApplicationStateConstant.MANUAL_REVIEW.getDescription())
                .build();

        application.setAmount(2_000_000.0);
        application.setType(LoanApplicationType.builder().id(2L).build());

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

        verify(userService).existsByIdentityNumber("12345");
        verify(loanApplicationTypeRepository).findById(2L);
        verify(loanApplicationStateRepository).findByName(LoanApplicationStateConstant.MANUAL_REVIEW.getName());
        verify(loanApplicationRepository).save(any());
    }
}
