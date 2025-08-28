package co.com.crediya.r2dbc.repository;

import co.com.crediya.r2dbc.entity.LoanApplicationStateEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface LoanApplicationStateReactiveRepository extends ReactiveCrudRepository<LoanApplicationStateEntity, Long> {
    Mono<LoanApplicationStateEntity> findByName(String name);
}
