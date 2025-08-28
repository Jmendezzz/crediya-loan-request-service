package co.com.crediya.r2dbc.repository;

import co.com.crediya.r2dbc.entity.LoanApplicationEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface LoanApplicationReactiveRepository extends ReactiveCrudRepository<LoanApplicationEntity, Long> {
}
