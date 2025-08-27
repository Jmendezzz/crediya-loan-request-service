package co.com.crediya.model.loanrequeststate.gateways;

import co.com.crediya.model.loanrequeststate.LoanRequestState;
import reactor.core.publisher.Mono;

public interface LoanRequestStateRepository {
    Mono<LoanRequestState> save(LoanRequestState loanRequestState);
    Mono<LoanRequestState> findByName(String name);
}
