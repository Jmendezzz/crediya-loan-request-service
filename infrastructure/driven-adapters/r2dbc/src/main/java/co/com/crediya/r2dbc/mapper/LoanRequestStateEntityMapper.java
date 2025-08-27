package co.com.crediya.r2dbc.mapper;

import co.com.crediya.model.loanrequeststate.LoanRequestState;
import co.com.crediya.r2dbc.entity.LoanRequestStateEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanRequestStateEntityMapper {
    LoanRequestState toDomain(LoanRequestStateEntity loanRequestStateEntity);
    LoanRequestStateEntity toEntity(LoanRequestState loanRequestState);
}
