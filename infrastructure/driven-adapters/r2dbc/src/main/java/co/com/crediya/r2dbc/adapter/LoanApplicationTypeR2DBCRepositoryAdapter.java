package co.com.crediya.r2dbc.adapter;

import co.com.crediya.model.loanapplicationtype.LoanApplicationType;
import co.com.crediya.model.loanapplicationtype.gateways.LoanApplicationTypeRepository;
import co.com.crediya.r2dbc.mapper.LoanApplicationTypeEntityMapper;
import co.com.crediya.r2dbc.repository.LoanApplicationTypeReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class LoanApplicationTypeR2DBCRepositoryAdapter implements LoanApplicationTypeRepository {
    private final LoanApplicationTypeReactiveRepository loanApplicationTypeReactiveRepository;
    private final LoanApplicationTypeEntityMapper loanApplicationTypeEntityMapper;

    @Override
    public Mono<LoanApplicationType> save(LoanApplicationType loanApplicationType) {
        return loanApplicationTypeReactiveRepository
                .save(loanApplicationTypeEntityMapper.toEntity(loanApplicationType))
                .map(loanApplicationTypeEntityMapper::toDomain);

    }

    @Override
    public Mono<LoanApplicationType> findByName(String name) {
        return loanApplicationTypeReactiveRepository
                .findByName(name)
                .map(loanApplicationTypeEntityMapper::toDomain);
    }

    @Override
    public Mono<LoanApplicationType> findById(Long id) {
        return loanApplicationTypeReactiveRepository
                .findById(id)
                .map(loanApplicationTypeEntityMapper::toDomain);
    }
}
