package co.com.crediya.api.rest.loanapplication.mapper;

import co.com.crediya.api.dto.PaginatedResponseDto;
import co.com.crediya.api.rest.loanapplication.dto.LoanApplicationResponseDto;
import co.com.crediya.model.common.results.Paginated;
import co.com.crediya.model.loanapplication.LoanApplication;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanApplicationResponseMapper {

    LoanApplicationResponseDto toDto(LoanApplication loanApplication);

    default PaginatedResponseDto<LoanApplicationResponseDto> toDtoPage(Paginated<LoanApplication> paginated) {
        List<LoanApplicationResponseDto> items = paginated.items()
                .stream()
                .map(this::toDto)
                .toList();

        return new PaginatedResponseDto<>(
                items,
                paginated.totalItems(),
                paginated.page(),
                paginated.size()
        );
    }
}