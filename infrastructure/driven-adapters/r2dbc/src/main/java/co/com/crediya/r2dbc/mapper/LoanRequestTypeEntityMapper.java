package co.com.crediya.r2dbc.mapper;

import co.com.crediya.model.loanrequesttype.LoanRequestType;
import co.com.crediya.r2dbc.entity.LoanRequestTypeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanRequestTypeEntityMapper {
    LoanRequestType toDomain(LoanRequestTypeEntity loanRequestTypeEntity);
    LoanRequestTypeEntity toEntity(LoanRequestType loanRequestType);
}
