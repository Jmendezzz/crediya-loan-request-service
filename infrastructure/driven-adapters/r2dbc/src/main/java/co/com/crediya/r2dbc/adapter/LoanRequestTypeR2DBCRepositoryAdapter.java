package co.com.crediya.r2dbc.adapter;

import co.com.crediya.model.loanrequesttype.LoanRequestType;
import co.com.crediya.model.loanrequesttype.gateways.LoanRequestTypeRepository;
import co.com.crediya.r2dbc.mapper.LoanRequestTypeEntityMapper;
import co.com.crediya.r2dbc.repository.LoanRequestTypeReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class LoanRequestTypeR2DBCRepositoryAdapter implements LoanRequestTypeRepository {
    private final LoanRequestTypeReactiveRepository loanRequestTypeReactiveRepository;
    private final LoanRequestTypeEntityMapper loanRequestTypeEntityMapper;

    @Override
    public Mono<LoanRequestType> save(LoanRequestType loanRequestType) {
        return loanRequestTypeReactiveRepository
                .save(loanRequestTypeEntityMapper.toEntity(loanRequestType))
                .map(loanRequestTypeEntityMapper::toDomain);

    }

    @Override
    public Mono<LoanRequestType> findByName(String name) {
        return loanRequestTypeReactiveRepository
                .findByName(name)
                .map(loanRequestTypeEntityMapper::toDomain);
    }
}
