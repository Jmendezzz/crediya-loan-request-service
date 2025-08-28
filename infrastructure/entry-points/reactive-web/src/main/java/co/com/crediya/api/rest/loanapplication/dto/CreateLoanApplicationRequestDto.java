package co.com.crediya.api.rest.loanapplication.dto;


import co.com.crediya.api.rest.loanapplication.constant.LoanApplicationOpenApiSchema;
import co.com.crediya.api.rest.loanapplication.constant.LoanApplicationValidationMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import static co.com.crediya.model.loanapplication.constants.LoanApplicationConstant.MIN_TERM_IN_MONTHS;

public record CreateLoanApplicationRequestDto(

        @NotBlank(message = LoanApplicationValidationMessage.IDENTITY_NUMBER_REQUIRED)
        @Schema(description = LoanApplicationOpenApiSchema.IDENTITY_NUMBER_DESCRIPTION,
                example = LoanApplicationOpenApiSchema.IDENTITY_NUMBER_EXAMPLE)
        String customerIdentityNumber,

        @NotNull(message = LoanApplicationValidationMessage.AMOUNT_REQUIRED)
        @Positive(message = LoanApplicationValidationMessage.AMOUNT_POSITIVE)
        @Schema(description = LoanApplicationOpenApiSchema.AMOUNT_DESCRIPTION,
                example = LoanApplicationOpenApiSchema.AMOUNT_EXAMPLE)
        Double amount,

        @NotNull(message = LoanApplicationValidationMessage.TERM_REQUIRED)
        @Min(value = MIN_TERM_IN_MONTHS, message = LoanApplicationValidationMessage.TERM_MIN)
        @Schema(description = LoanApplicationOpenApiSchema.TERM_DESCRIPTION,
                example = LoanApplicationOpenApiSchema.TERM_EXAMPLE)
        Integer termInMonths,

        @NotNull(message = LoanApplicationValidationMessage.TYPE_REQUIRED)
        @Schema(description = LoanApplicationOpenApiSchema.TYPE_ID_DESCRIPTION,
                example = LoanApplicationOpenApiSchema.TYPE_ID_EXAMPLE)
        Long typeId,

        @Schema(description = LoanApplicationOpenApiSchema.STATE_ID_DESCRIPTION,
                example = LoanApplicationOpenApiSchema.STATE_ID_EXAMPLE)
        Long stateId
) {}