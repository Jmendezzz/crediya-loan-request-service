package co.com.crediya.api.rest.loanapplication.mapper;

import co.com.crediya.api.rest.loanapplication.dto.LoanApplicationResponseDto;
import co.com.crediya.model.loanapplication.LoanApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanApplicationResponseMapper {
    LoanApplicationResponseDto toDto(LoanApplication loanApplication);
}
