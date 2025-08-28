package co.com.crediya.api.rest.loanapplication.dto;

import co.com.crediya.api.rest.loanapplication.constant.LoanApplicationOpenApiSchema;
import co.com.crediya.model.loanapplicationstate.LoanApplicationState;
import co.com.crediya.model.loanapplicationtype.LoanApplicationType;
import io.swagger.v3.oas.annotations.media.Schema;

public record LoanApplicationResponseDto(

        @Schema(description = LoanApplicationOpenApiSchema.ID_DESCRIPTION)
        Long id,

        @Schema(description = LoanApplicationOpenApiSchema.IDENTITY_NUMBER_DESCRIPTION,
                example = LoanApplicationOpenApiSchema.IDENTITY_NUMBER_EXAMPLE)
        String customerIdentityNumber,

        @Schema(description = LoanApplicationOpenApiSchema.AMOUNT_DESCRIPTION,
                example = LoanApplicationOpenApiSchema.AMOUNT_EXAMPLE)
        Double amount,

        @Schema(description = LoanApplicationOpenApiSchema.TERM_DESCRIPTION,
                example = LoanApplicationOpenApiSchema.TERM_EXAMPLE)
        Integer termInMonths,

        @Schema(description = LoanApplicationOpenApiSchema.TYPE_SCHEMA_DESCRIPTION)
        LoanApplicationType type,

        @Schema(description = LoanApplicationOpenApiSchema.STATE_SCHEMA_DESCRIPTION)
        LoanApplicationState state
) {}