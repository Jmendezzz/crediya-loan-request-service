package co.com.crediya.config.seeder;

import co.com.crediya.model.loanrequeststate.LoanRequestState;
import co.com.crediya.model.loanrequeststate.constants.LoanRequestStateConstant;
import co.com.crediya.model.loanrequeststate.gateways.LoanRequestStateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoanRequestStateSeeder implements CommandLineRunner {

    private final LoanRequestStateRepository repository;

    @Override
    public void run(String... args) throws Exception {
        Flux.just(
                        new LoanRequestState(null, LoanRequestStateConstant.PENDING_REVIEW, LoanRequestStateConstant.PENDING_REVIEW_DESC),
                        new LoanRequestState(null, LoanRequestStateConstant.APPROVED, LoanRequestStateConstant.APPROVED_DESC),
                        new LoanRequestState(null, LoanRequestStateConstant.REJECTED, LoanRequestStateConstant.REJECTED_DESC),
                        new LoanRequestState(null, LoanRequestStateConstant.CANCELED, LoanRequestStateConstant.CANCELED_DESC)
                )
                .flatMap(state ->
                        repository.findByName(state.getName())
                        .switchIfEmpty(repository.save(state))
                )
                .doOnNext(state -> log.info("State created/verified: {} - {}", state.getName(), state.getDescription()))
                .subscribe();
    }
}