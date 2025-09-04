package co.com.crediya.api.rest.loanapplication;

import co.com.crediya.api.dto.PageRequestDto;
import co.com.crediya.api.dto.PaginatedResponseDto;
import co.com.crediya.api.rest.loanapplication.constant.LoanApplicationHandlerLog;
import co.com.crediya.api.rest.loanapplication.dto.CreateLoanApplicationRequestDto;
import co.com.crediya.api.rest.loanapplication.dto.LoanApplicationFilterRequestDto;
import co.com.crediya.api.rest.loanapplication.dto.LoanApplicationResponseDto;
import co.com.crediya.api.rest.loanapplication.dto.UpdateLoanApplicationStateRequestDto;
import co.com.crediya.api.rest.loanapplication.mapper.LoanApplicationRequestMapper;
import co.com.crediya.api.rest.loanapplication.mapper.LoanApplicationResponseMapper;
import co.com.crediya.api.utils.ObjectValidator;
import co.com.crediya.model.loanapplication.commands.UpdateLoanApplicationStateCommand;
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
                .doOnSubscribe(s -> log.info(LoanApplicationHandlerLog.CREATE_VALIDATION.getMessage()))
                .map(requestMapper::toDomain)
                .doOnNext(domain -> log.info(LoanApplicationHandlerLog.CREATE_START.getMessage(), dto))
                .flatMap(loanApplicationUseCase::createLoanApplication)
                .doOnSuccess(app -> log.info(LoanApplicationHandlerLog.CREATE_SUCCESS.getMessage(), app.getId()))
                .doOnError(error -> log.error(LoanApplicationHandlerLog.CREATE_ERROR.getMessage(), error.getMessage(), error))
                .map(responseMapper::toDto);
    }

    public Mono<PaginatedResponseDto<LoanApplicationResponseDto>> getLoanApplications(
            LoanApplicationFilterRequestDto filterDto,
            PageRequestDto pageRequestDto
    ) {
        return validator.validate(filterDto)
                .then(validator.validate(pageRequestDto))
                .doOnSubscribe(s -> log.info(
                        LoanApplicationHandlerLog.GET_START.getMessage(), filterDto, pageRequestDto))
                .flatMap(v -> loanApplicationUseCase.getLoanApplications(
                                requestMapper.toPagedQuery(filterDto, pageRequestDto))
                        .map(responseMapper::toDtoPage)
                        .doOnSuccess(res -> log.info(
                                LoanApplicationHandlerLog.GET_SUCCESS.getMessage(), res.items().size()))
                        .doOnError(e -> log.error(
                                LoanApplicationHandlerLog.GET_ERROR.getMessage(), e.getMessage(), e))
                );
    }
    public Mono<LoanApplicationResponseDto> updateLoanApplicationState(
            Long loanApplicationId,
            UpdateLoanApplicationStateRequestDto dto
    ) {
        return validator.validate(dto)
                .doOnSubscribe(s -> log.info(
                        LoanApplicationHandlerLog.UPDATE_STATE_START.getMessage(), loanApplicationId, dto))
                .flatMap(v -> {
                    UpdateLoanApplicationStateCommand command =
                            new UpdateLoanApplicationStateCommand(loanApplicationId, dto.stateId());

                    return loanApplicationUseCase.updateLoanApplicationState(command)
                            .doOnSuccess(app -> log.info(
                                    LoanApplicationHandlerLog.UPDATE_STATE_SUCCESS.getMessage(), loanApplicationId))
                            .doOnError(error -> log.error(
                                    LoanApplicationHandlerLog.UPDATE_STATE_ERROR.getMessage(),
                                    loanApplicationId, error.getMessage(), error))
                            .map(responseMapper::toDto);
                });
    }
}