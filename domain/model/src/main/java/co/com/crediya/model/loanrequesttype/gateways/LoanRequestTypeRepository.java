package co.com.crediya.model.loanrequesttype.gateways;

import co.com.crediya.model.loanrequesttype.LoanRequestType;
import reactor.core.publisher.Mono;

public interface LoanRequestTypeRepository {
    Mono<LoanRequestType> save(LoanRequestType loanRequestType);
    Mono<LoanRequestType> findByName(String name);
}
