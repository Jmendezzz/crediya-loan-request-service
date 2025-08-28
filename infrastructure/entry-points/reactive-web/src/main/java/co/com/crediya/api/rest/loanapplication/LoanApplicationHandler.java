package co.com.crediya.api.rest.loanapplication;

import co.com.crediya.api.rest.loanapplication.dto.CreateLoanApplicationRequestDto;
import co.com.crediya.api.rest.loanapplication.dto.LoanApplicationResponseDto;
import co.com.crediya.api.rest.loanapplication.mapper.LoanApplicationRequestMapper;
import co.com.crediya.api.rest.loanapplication.mapper.LoanApplicationResponseMapper;
import co.com.crediya.api.utils.ObjectValidator;
import co.com.crediya.usecase.loanapplication.LoanApplicationUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoanApplicationHandler {

    private final LoanApplicationUseCase loanApplicationUseCase;
    private final LoanApplicationRequestMapper requestMapper;
    private final LoanApplicationResponseMapper responseMapper;
    private final ObjectValidator validator;

    public Mono<LoanApplicationResponseDto> createApplicant(CreateLoanApplicationRequestDto dto) {
        return validator.validate(dto)
                .map(requestMapper::toDomain)
                .flatMap(loanApplicationUseCase::createLoanApplication)
                .map(responseMapper::toDto);
    }
}