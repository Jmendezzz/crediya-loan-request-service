package co.com.crediya.model.loanapplication.gateways;

import co.com.crediya.model.common.exceptions.PagedQuery;
import co.com.crediya.model.common.exceptions.Paginated;
import co.com.crediya.model.loanapplication.LoanApplication;
import co.com.crediya.model.loanapplication.LoanApplicationQuery;
import reactor.core.publisher.Mono;

public interface LoanApplicationRepository {
    Mono<LoanApplication> save(LoanApplication loanApplication);
    Mono<Paginated<LoanApplication>> findByCriteria(PagedQuery<LoanApplicationQuery> query);
}
