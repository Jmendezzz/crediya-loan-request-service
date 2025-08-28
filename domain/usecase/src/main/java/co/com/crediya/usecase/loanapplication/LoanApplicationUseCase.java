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
import co.com.crediya.model.loanapplicationtype.exceptions.LoanApplicationTypeNotFoundException;
import co.com.crediya.model.loanapplicationtype.gateways.LoanApplicationTypeRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

@RequiredArgsConstructor
public class LoanApplicationUseCase {

    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanApplicationTypeRepository loanApplicationTypeRepository;
    private final LoanApplicationStateRepository loanApplicationStateRepository;
    private final UserService userService;

    public Mono<LoanApplication> createLoanApplication(LoanApplication application) {
        return validateUser(application.getCustomerIdentityNumber())
                .then(Mono.defer(() -> validateType(application.getType().getId())
                        .doOnNext(type -> validateAmount(application.getAmount(), type))
                        .flatMap(type -> validateInitialState(type)
                                .map(state -> Tuples.of(type, state)))))
                .flatMap(tuple -> {
                    LoanApplicationType type = tuple.getT1();
                    LoanApplicationState state = tuple.getT2();

                    application.setType(type);
                    application.setState(state);
                    return loanApplicationRepository.save(application);
                });
    }

    private Mono<Void> validateUser(String identityNumber) {
        return userService.existsByIdentityNumber(identityNumber)
                .flatMap(exists -> exists ? Mono.empty() : Mono.error(new CustomerNotFoundException(identityNumber)));
        }

    private Mono<LoanApplicationType> validateType(Long typeId) {
        return loanApplicationTypeRepository.findById(typeId)
                .switchIfEmpty(Mono.error(new LoanApplicationTypeNotFoundException()));
    }

    private void validateAmount(Double requested, LoanApplicationType type) {
        if (requested < type.getMinAmount() || requested > type.getMaxAmount()) {
            throw new LoanApplicationAmountOutOfRangeException(
                    requested,
                    type.getMinAmount(),
                    type.getMaxAmount()
            );
        }
    }

    private Mono<LoanApplicationState> validateInitialState(LoanApplicationType type) {
        String stateName = type.getAutoValidation()
                ? LoanApplicationStateConstant.PENDING_REVIEW.getName()
                : LoanApplicationStateConstant.MANUAL_REVIEW.getName();

        return loanApplicationStateRepository.findByName(stateName)
                .switchIfEmpty(Mono.error(new LoanApplicationStateNotFoundException()));
    }
}
