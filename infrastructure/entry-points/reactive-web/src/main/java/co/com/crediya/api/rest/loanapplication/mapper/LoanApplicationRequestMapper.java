package co.com.crediya.api.rest.loanapplication.mapper;

import co.com.crediya.api.rest.loanapplication.dto.CreateLoanApplicationRequestDto;
import co.com.crediya.model.loanapplication.LoanApplication;
import co.com.crediya.model.loanapplicationstate.LoanApplicationState;
import co.com.crediya.model.loanapplicationtype.LoanApplicationType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface LoanApplicationRequestMapper {

    @Mapping(target = "type", source = "typeId", qualifiedByName = "mapTypeIdToType")
    @Mapping(target = "state", source = "stateId", qualifiedByName = "mapStateIdToState")
    LoanApplication toDomain(CreateLoanApplicationRequestDto dto);

    @Named("mapTypeIdToType")
    default LoanApplicationType mapTypeIdToType(Long typeId) {
        return typeId != null ? LoanApplicationType.builder().id(typeId).build() : null;
    }

    @Named("mapStateIdToState")
    default LoanApplicationState mapStateIdToState(Long stateId) {
        return stateId != null ? LoanApplicationState.builder().id(stateId).build() : null;
    }
}
