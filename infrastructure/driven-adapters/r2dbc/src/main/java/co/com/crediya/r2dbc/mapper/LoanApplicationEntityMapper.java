package co.com.crediya.r2dbc.mapper;

import co.com.crediya.model.loanapplication.LoanApplication;
import co.com.crediya.model.loanapplicationstate.LoanApplicationState;
import co.com.crediya.model.loanapplicationtype.LoanApplicationType;
import co.com.crediya.r2dbc.entity.LoanApplicationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoanApplicationEntityMapper {
    @Mapping(source = "state.id", target = "stateId")
    @Mapping(source = "type.id", target = "typeId")
    LoanApplicationEntity toEntity(LoanApplication loanApplication);

    @Mapping(source = "loanApplication.id", target = "id")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "type", target = "type")
    LoanApplication toDomain(LoanApplicationEntity loanApplication, LoanApplicationState state, LoanApplicationType type);
}
