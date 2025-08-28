package co.com.crediya.r2dbc.mapper;

import co.com.crediya.model.loanapplicationstate.LoanApplicationState;
import co.com.crediya.r2dbc.entity.LoanApplicationStateEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanApplicationStateEntityMapper {
    LoanApplicationState toDomain(LoanApplicationStateEntity loanApplicationStateEntity);
    LoanApplicationStateEntity toEntity(LoanApplicationState loanApplicationState);
}
