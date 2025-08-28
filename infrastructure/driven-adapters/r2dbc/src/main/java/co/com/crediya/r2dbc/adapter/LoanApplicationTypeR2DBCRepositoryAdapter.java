package co.com.crediya.r2dbc.adapter;

import co.com.crediya.model.loanapplicationtype.LoanApplicationType;
import co.com.crediya.model.loanapplicationtype.gateways.LoanApplicationTypeRepository;
import co.com.crediya.r2dbc.mapper.LoanRequestTypeEntityMapper;
import co.com.crediya.r2dbc.repository.LoanRequestTypeReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class LoanApplicationTypeR2DBCRepositoryAdapter implements LoanApplicationTypeRepository {
    private final LoanRequestTypeReactiveRepository loanRequestTypeReactiveRepository;
    private final LoanRequestTypeEntityMapper loanRequestTypeEntityMapper;

    @Override
    public Mono<LoanApplicationType> save(LoanApplicationType loanApplicationType) {
        return loanRequestTypeReactiveRepository
                .save(loanRequestTypeEntityMapper.toEntity(loanApplicationType))
                .map(loanRequestTypeEntityMapper::toDomain);

    }

    @Override
    public Mono<LoanApplicationType> findByName(String name) {
        return loanRequestTypeReactiveRepository
                .findByName(name)
                .map(loanRequestTypeEntityMapper::toDomain);
    }
}
