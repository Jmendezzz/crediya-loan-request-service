package co.com.crediya.r2dbc.adapter;

import co.com.crediya.model.loanrequeststate.LoanRequestState;
import co.com.crediya.model.loanrequeststate.gateways.LoanRequestStateRepository;
import co.com.crediya.r2dbc.mapper.LoanRequestStateEntityMapper;
import co.com.crediya.r2dbc.repository.LoanRequestStateReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class LoanRequestStateR2DBCRepositoryAdapter implements LoanRequestStateRepository {
    private final LoanRequestStateReactiveRepository loanRequestStateReactiveRepository;
    private final LoanRequestStateEntityMapper loanRequestStateEntityMapper;

    @Override
    public Mono<LoanRequestState> save(LoanRequestState loanRequestState) {
        return loanRequestStateReactiveRepository
                .save(loanRequestStateEntityMapper.toEntity(loanRequestState))
                .map(loanRequestStateEntityMapper::toDomain);
    }

    @Override
    public Mono<LoanRequestState> findByName(String name) {
        return loanRequestStateReactiveRepository
                .findByName(name)
                .map(loanRequestStateEntityMapper::toDomain);
    }
}
