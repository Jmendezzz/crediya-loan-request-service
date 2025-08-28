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
    public void run(String... args) {
        Flux.fromArray(LoanApplicationStateConstant.values())
                .flatMap(stateEnum -> repository.findByName(stateEnum.getName())
                        .switchIfEmpty(
                                repository.save(
                                        LoanApplicationState.builder()
                                                .name(stateEnum.getName())
                                                .description(stateEnum.getDescription())
                                                .build()
                                )
                        )
                )
                .doOnNext(state -> log.info("Loan state created/verified: {} - {}", state.getName(), state.getDescription()))
                .then()
                .block();
    }
}
