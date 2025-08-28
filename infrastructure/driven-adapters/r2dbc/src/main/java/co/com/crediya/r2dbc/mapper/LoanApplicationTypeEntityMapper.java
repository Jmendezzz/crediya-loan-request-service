package co.com.crediya.r2dbc.mapper;

import co.com.crediya.model.loanapplicationtype.LoanApplicationType;
import co.com.crediya.r2dbc.entity.LoanApplicationTypeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanRequestTypeEntityMapper {
    LoanApplicationType toDomain(LoanApplicationTypeEntity loanApplicationTypeEntity);
    LoanApplicationTypeEntity toEntity(LoanApplicationType loanApplicationType);
}
