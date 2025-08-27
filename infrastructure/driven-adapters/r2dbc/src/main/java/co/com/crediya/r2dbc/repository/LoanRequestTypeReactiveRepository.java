package co.com.crediya.r2dbc.repository;

import co.com.crediya.r2dbc.entity.LoanRequestTypeEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface LoanRequestTypeReactiveRepository extends ReactiveCrudRepository<LoanRequestTypeEntity, Long> {
    Mono<LoanRequestTypeEntity> findByName(String name);
}
