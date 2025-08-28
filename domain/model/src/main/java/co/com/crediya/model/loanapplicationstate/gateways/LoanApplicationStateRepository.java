package co.com.crediya.model.loanapplicationstate.gateways;

import co.com.crediya.model.loanapplicationstate.LoanApplicationState;
import reactor.core.publisher.Mono;

public interface LoanApplicationStateRepository {
    Mono<LoanApplicationState> save(LoanApplicationState loanApplicationState);
    Mono<LoanApplicationState> findByName(String name);
}
