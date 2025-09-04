package co.com.crediya.usecase.loanapplication;

import co.com.crediya.model.auth.gateways.AuthService;
import co.com.crediya.model.common.queries.PagedQuery;
import co.com.crediya.model.common.results.Paginated;
import co.com.crediya.model.loanapplication.LoanApplication;
import co.com.crediya.model.loanapplication.commands.UpdateLoanApplicationStateCommand;
import co.com.crediya.model.loanapplication.events.LoanApplicationStateChanged;
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
import co.com.crediya.model.loanapplicationtype.exceptions.LoanApplicationTypeNotFoundException;
import co.com.crediya.model.loanapplicationtype.gateways.LoanApplicationTypeRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

@RequiredArgsConstructor
public class LoanApplicationUseCase {

    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanApplicationTypeRepository loanApplicationTypeRepository;
    private final LoanApplicationStateRepository loanApplicationStateRepository;

    private final UserService userService;
    private final AuthService authService;

    private final LoanApplicationEventPublisher loanApplicationEventPublisher;

    public Mono<LoanApplication> createLoanApplication(LoanApplication application) {
        return validateOwnership(application.getCustomerIdentityNumber())
                .then(validateUser(application.getCustomerIdentityNumber()))
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


    private Mono<Void> validateOwnership(String identityNumber) {
        return authService.getCurrentUser()
                .flatMap(userAuth -> {
                    if (!userAuth.identityNumber().equals(identityNumber)) {
                        return Mono.error(new UnauthorizedLoanApplicationException());
                    }
                    return Mono.empty();
                });
    }

    private Mono<Void> validateUser(String identityNumber) {
        return userService.existsByIdentityNumber(identityNumber)
                .flatMap(exists -> exists
                        ? Mono.empty()
                        : Mono.error(new CustomerNotFoundException(identityNumber)));
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

    public Mono<Paginated<LoanApplication>> getLoanApplications(PagedQuery<LoanApplicationQuery> query){
        return loanApplicationRepository.findByCriteria(query);
    }

    public Mono<LoanApplication> updateLoanApplicationState(UpdateLoanApplicationStateCommand command) {
        return findApplicationAndState(command)
                .flatMap(this::enrichWithUser)
                .flatMap(this::saveAndPublish);
    }

    private Mono<Tuple2<LoanApplication, LoanApplicationState>> findApplicationAndState(UpdateLoanApplicationStateCommand command) {
        return loanApplicationRepository.findById(command.loanApplicationId())
                .switchIfEmpty(Mono.error(new LoanApplicationNotFoundException()))
                .zipWhen(app -> loanApplicationStateRepository.findById(command.stateId())
                        .switchIfEmpty(Mono.error(new LoanApplicationStateNotFoundException())));
    }

    private Mono<Tuple2<LoanApplication, User>> enrichWithUser(Tuple2<LoanApplication, LoanApplicationState> tuple) {
        LoanApplication app = tuple.getT1();
        LoanApplicationState state = tuple.getT2();

        app.setState(state);

        return userService.getUserByIdentityNumber(app.getCustomerIdentityNumber())
                .map(user -> Tuples.of(app, user));
    }

    private Mono<LoanApplication> saveAndPublish(Tuple2<LoanApplication, User> tuple) {
        LoanApplication app = tuple.getT1();
        User user = tuple.getT2();

        LoanApplicationStateChanged event = LoanApplicationStateChanged.builder()
                .loanApplicationId(app.getId())
                .loanApplicationNewState(app.getState().getName())
                .customerIdentityNumber(app.getCustomerIdentityNumber())
                .customerEmail(user.email())
                .customerFirstName(user.firstName())
                .customerLastName(user.lastName())
                .build();

        return loanApplicationRepository.save(app)
                .flatMap(saved -> loanApplicationEventPublisher.publish(event)
                        .thenReturn(saved));
    }

}
