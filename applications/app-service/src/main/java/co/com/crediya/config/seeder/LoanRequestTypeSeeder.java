package co.com.crediya.config.seeder;

import co.com.crediya.model.loanrequesttype.LoanRequestType;
import co.com.crediya.model.loanrequesttype.constants.LoanRequestTypeConstant;
import co.com.crediya.model.loanrequesttype.gateways.LoanRequestTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;


@Component
@RequiredArgsConstructor
@Slf4j
public class LoanRequestTypeSeeder implements CommandLineRunner {
    private final LoanRequestTypeRepository repository;

    @Override
    public void run(String... args) {
        Flux.fromArray(LoanRequestTypeConstant.values())
                .flatMap(typeEnum -> repository.findByName(typeEnum.getName())
                        .switchIfEmpty(
                                repository.save(
                                    LoanRequestType.builder()
                                            .name(typeEnum.getName())
                                            .description(typeEnum.getDescription())
                                            .autoValidation(typeEnum.getAutoValidation())
                                            .interestRate(typeEnum.getInterestRate())
                                            .minAmount(typeEnum.getMinAmount())
                                            .maxAmount(typeEnum.getMaxAmount())
                                            .build()
                                )
                        )
                )
                .doOnNext(type -> log.info("Loan type created/verified: {} - {}", type.getName(), type.getDescription()))
                .then()
                .block();
    }
}
