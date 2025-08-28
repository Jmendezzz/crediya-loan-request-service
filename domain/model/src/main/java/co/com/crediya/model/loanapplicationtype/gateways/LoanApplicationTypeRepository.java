package co.com.crediya.model.loanapplicationtype.gateways;

import co.com.crediya.model.loanapplicationtype.LoanApplicationType;
import reactor.core.publisher.Mono;

public interface LoanApplicationTypeRepository {
    Mono<LoanApplicationType> save(LoanApplicationType loanApplicationType);
    Mono<LoanApplicationType> findByName(String name);
}
