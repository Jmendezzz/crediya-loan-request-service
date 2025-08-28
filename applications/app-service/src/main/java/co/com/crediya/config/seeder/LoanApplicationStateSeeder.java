package co.com.crediya.config.seeder;

import co.com.crediya.model.loanapplicationstate.LoanApplicationState;
import co.com.crediya.model.loanapplicationstate.constants.LoanApplicationStateConstant;
import co.com.crediya.model.loanapplicationstate.gateways.LoanApplicationStateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoanApplicationStateSeeder implements CommandLineRunner {

    private final LoanApplicationStateRepository repository;

    @Override
    public void run(String... args) throws Exception {
        Flux.just(
                        new LoanApplicationState(null, LoanApplicationStateConstant.PENDING_REVIEW, LoanApplicationStateConstant.PENDING_REVIEW_DESC),
                        new LoanApplicationState(null, LoanApplicationStateConstant.APPROVED, LoanApplicationStateConstant.APPROVED_DESC),
                        new LoanApplicationState(null, LoanApplicationStateConstant.REJECTED, LoanApplicationStateConstant.REJECTED_DESC),
                        new LoanApplicationState(null, LoanApplicationStateConstant.CANCELED, LoanApplicationStateConstant.CANCELED_DESC)
                )
                .flatMap(state ->
                        repository.findByName(state.getName())
                        .switchIfEmpty(repository.save(state))
                )
                .doOnNext(state -> log.info("State created/verified: {} - {}", state.getName(), state.getDescription()))
                .subscribe();
    }
}