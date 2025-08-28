package co.com.crediya.r2dbc.repository;

import co.com.crediya.r2dbc.entity.LoanApplicationTypeEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface LoanApplicationTypeReactiveRepository extends ReactiveCrudRepository<LoanApplicationTypeEntity, Long> {
    Mono<LoanApplicationTypeEntity> findByName(String name);
}
