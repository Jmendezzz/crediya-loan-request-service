package co.com.crediya.r2dbc.repository;

import co.com.crediya.r2dbc.entity.LoanRequestStateEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface LoanRequestStateReactiveRepository extends ReactiveCrudRepository<LoanRequestStateEntity, Long> {
    Mono<LoanRequestStateEntity> findByName(String name);
}
