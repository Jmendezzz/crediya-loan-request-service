package co.com.crediya.api.rest.loanapplication.dto;

import co.com.crediya.api.rest.loanapplication.constant.LoanApplicationOpenApiSchema;
import co.com.crediya.api.rest.loanapplication.constant.LoanApplicationValidationMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record UpdateLoanApplicationStateRequestDto(
        @Schema(description = LoanApplicationOpenApiSchema.STATE_ID_DESCRIPTION, example = LoanApplicationOpenApiSchema.STATE_ID_EXAMPLE)
        @NotNull(message = LoanApplicationValidationMessage.STATE_REQUIRED)
        Long stateId
) { }