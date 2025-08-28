package co.com.crediya.r2dbc.adapter;

import co.com.crediya.model.loanapplication.LoanApplication;
import co.com.crediya.model.loanapplication.gateways.LoanApplicationRepository;
import co.com.crediya.r2dbc.entity.LoanApplicationEntity;
import co.com.crediya.r2dbc.mapper.LoanApplicationEntityMapper;
import co.com.crediya.r2dbc.mapper.LoanApplicationStateEntityMapper;
import co.com.crediya.r2dbc.mapper.LoanApplicationTypeEntityMapper;
import co.com.crediya.r2dbc.repository.LoanApplicationReactiveRepository;
import co.com.crediya.r2dbc.repository.LoanApplicationStateReactiveRepository;
import co.com.crediya.r2dbc.repository.LoanApplicationTypeReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class LoanApplicationR2DBCRepository implements LoanApplicationRepository {

    private final LoanApplicationReactiveRepository loanApplicationReactiveRepository;
    private final LoanApplicationTypeReactiveRepository loanApplicationTypeReactiveRepository;
    private final LoanApplicationStateReactiveRepository loanApplicationStateReactiveRepository;
    private final TransactionalOperator transactionalOperator;

    private final LoanApplicationEntityMapper loanApplicationEntityMapper;
    private final LoanApplicationTypeEntityMapper loanApplicationTypeEntityMapper;
    private final LoanApplicationStateEntityMapper loanApplicationStateEntityMapper;

    @Override
    public Mono<LoanApplication> save(LoanApplication loanApplication) {
        return transactionalOperator.transactional(
                loanApplicationReactiveRepository
                        .save(loanApplicationEntityMapper.toEntity(loanApplication))
                        .flatMap(this::enrichWithRelations)
        );
    }

    private Mono<LoanApplication> enrichWithRelations(LoanApplicationEntity loanApplication) {
        return Mono.zip(
                loanApplicationTypeReactiveRepository.findById(loanApplication.getTypeId()),
                loanApplicationStateReactiveRepository.findById(loanApplication.getStateId())
        ).map(tuple -> loanApplicationEntityMapper.toDomain(
                loanApplication,
                loanApplicationStateEntityMapper.toDomain(tuple.getT2()),
                loanApplicationTypeEntityMapper.toDomain(tuple.getT1())

                ));
    }
}