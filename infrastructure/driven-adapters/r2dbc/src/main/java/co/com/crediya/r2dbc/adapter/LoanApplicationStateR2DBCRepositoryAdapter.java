package co.com.crediya.r2dbc.adapter;

import co.com.crediya.model.loanapplicationstate.LoanApplicationState;
import co.com.crediya.model.loanapplicationstate.gateways.LoanApplicationStateRepository;
import co.com.crediya.r2dbc.mapper.LoanApplicationStateEntityMapper;
import co.com.crediya.r2dbc.repository.LoanApplicationStateReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class LoanApplicationStateR2DBCRepositoryAdapter implements LoanApplicationStateRepository {
    private final LoanApplicationStateReactiveRepository loanApplicationStateReactiveRepository;
    private final LoanApplicationStateEntityMapper loanApplicationStateEntityMapper;

    @Override
    public Mono<LoanApplicationState> save(LoanApplicationState loanApplicationState) {
        return loanApplicationStateReactiveRepository
                .save(loanApplicationStateEntityMapper.toEntity(loanApplicationState))
                .map(loanApplicationStateEntityMapper::toDomain);
    }

    @Override
    public Mono<LoanApplicationState> findByName(String name) {
        return loanApplicationStateReactiveRepository
                .findByName(name)
                .map(loanApplicationStateEntityMapper::toDomain);
    }

    @Override
    public Mono<LoanApplicationState> findById(Long id) {
        return loanApplicationStateReactiveRepository
                .findById(id)
                .map(loanApplicationStateEntityMapper::toDomain);
    }
}
