package co.com.crediya.model.loanapplication.gateways;

import co.com.crediya.model.common.queries.PagedQuery;
import co.com.crediya.model.common.results.Paginated;
import co.com.crediya.model.loanapplication.LoanApplication;
import co.com.crediya.model.loanapplication.queries.LoanApplicationQuery;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LoanApplicationRepository {
    Mono<LoanApplication> save(LoanApplication loanApplication);
    Mono<Paginated<LoanApplication>> findByCriteria(PagedQuery<LoanApplicationQuery> query);
    Mono<LoanApplication> findById(Long id);
    Flux<LoanApplication> findApprovedByCustomerIdentityNumber(String identityNumber);
}
